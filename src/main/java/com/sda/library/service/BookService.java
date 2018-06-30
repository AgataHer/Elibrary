package com.sda.library.service;

import com.sda.library.exception.BookNotFoundException;
import com.sda.library.model.Author;
import com.sda.library.model.Book;
import com.sda.library.model.Category;
import com.sda.library.model.Publisher;
import com.sda.library.repository.BookRepository;
import com.sda.library.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    @Autowired
    public BookService(final BookRepository bookRepository, final PublisherService publisherService,
                       final CategoryService categoryService, final AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.publisherService = publisherService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Book book) { bookRepository.delete(book); }

    public Book getBookById(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );
        return book;
    }

    public static Book getBookByIsbnFromWeb(String isbn) {
        Book book = new Book();
        try {
            isbn = isbn.replace("-", "");
            JSONObject jsonObject = JsonUtils.readJsonFromUrl("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
            book.setJsonInfo(jsonObject.toString());

            for (int i = 0; i < jsonObject.length(); i++) {
                Iterator<?> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.equals("items")) {
                        JSONArray items = jsonObject.getJSONArray("items");
                        for (int j = 0; j < items.length(); j++) {
                            JSONObject itemObject = items.getJSONObject(j);
                            String selfLink = itemObject.optString("selfLink");
                            if (selfLink != null) {
                                book.setSelfLink(selfLink);
                            }

                            String itemSaleInfo = itemObject.optString("saleInfo");
                            JSONObject jsonSaleInfo = new JSONObject(itemSaleInfo);
                            String itemCountry = jsonSaleInfo.optString("country");
                            if (itemCountry != null) {
                                book.setLanguage(itemCountry);
                            }

                            String itemVolumeInfo = itemObject.optString("volumeInfo");
                            JSONObject jsonVolumeInfo = new JSONObject(itemVolumeInfo);
                            String itemTitle = jsonVolumeInfo.optString("title");
                            if (itemTitle != null) {
                                book.setTitle(itemTitle);
                            }

                            String itemSubtitle = jsonVolumeInfo.optString("subtitle");
                            if (itemSubtitle != null) {
                                book.setSubtitle(itemSubtitle);
                            }

                            String itemPublishedDate = jsonVolumeInfo.optString("publishedDate");
                            if (itemPublishedDate != null) {
                                itemPublishedDate = itemPublishedDate.substring(0,4);
                                book.setPublishedDate(Integer.parseInt(itemPublishedDate));
                            }

                            String itemPageCount = jsonVolumeInfo.optString("pageCount");
                            if (itemPageCount != null) {
                                book.setPageCount(Integer.parseInt(itemPageCount));
                            }

                            String itemDescription = jsonVolumeInfo.optString("description");
                            if (itemDescription  != null) {
                                book.setDescription(itemDescription);
                            }

                            JSONArray itemAuthors = jsonVolumeInfo.optJSONArray("authors");
                            if (itemDescription != null) {
                                Set<Author> itemAuthorsSet = new HashSet<>();
                                for (int k = 0; k < itemAuthors.length(); k++) {
                                    itemAuthorsSet.add(new Author(itemAuthors.getString(k)));
                                }
                                book.setAuthors(itemAuthorsSet);
                            }

                            JSONArray itemCategories = jsonVolumeInfo.optJSONArray("categories");
                            if (itemCategories != null) {
                                Set<Category> itemCategoriesSet = new HashSet<>();
                                for (int k = 0; k < itemCategories.length(); k++) {
                                    Category category = new Category();
                                    category.setName(itemCategories.getString(k));
                                    itemCategoriesSet.add(category);
                                }
                                book.setCategories(itemCategoriesSet);
                            }

                            String itemPublisher = jsonVolumeInfo.optString("publisher");
                            if (itemPublisher != null) {
                                Publisher publisher = new Publisher();
                                publisher.setName(itemPublisher);
                                book.setPublisher(publisher);
                            }

                            Iterator<?> keysII = jsonVolumeInfo.keys();
                            while (keysII.hasNext()) {
                                String keyII = (String) keysII.next();
                                if (keyII.equals("industryIdentifiers")) {
                                    JSONArray itemsII = jsonVolumeInfo.getJSONArray("industryIdentifiers");
                                    for (int k = 0; k < itemsII.length(); k++) {
                                        JSONObject itemsIIObject = (JSONObject) itemsII.get(k);
                                        String itemsIIType = itemsIIObject.optString("type");
                                        String itemsIIIdentifier = itemsIIObject.optString("identifier");
                                        if ((itemsIIType != null) && (itemsIIIdentifier != null)) {
                                            switch (itemsIIType) {
                                                case "ISBN_10":
                                                    book.setIsbn10(itemsIIIdentifier);
                                                    break;
                                                case "ISBN_13":
                                                    book.setIsbn13(itemsIIIdentifier);
                                                    break;
                                            }
                                        }
                                    }
                                }
                                if (keyII.equals("imageLinks")) {
                                    System.out.println("");
                                    JSONObject imageLinks = jsonVolumeInfo.getJSONObject("imageLinks");
                                    String thumbnail = imageLinks.optString("thumbnail");
                                    if (thumbnail != null) {
                                        book.setCoverLink(thumbnail);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

    @Transactional
    public Book saveComplex(Book book){
        // save publisher
        book.setPublisher(publisherService.saveUnique(book.getPublisher().getName()));
        // save categories
        book.setCategories(categoryService.saveUnique(book.getCategories()));
        // save authors
        book.setAuthors(authorService.saveUnique(book.getAuthors()));
        // save book
        if (book.getJsonInfo().length() > 255) {
            book.setJsonInfo(null);
        }

        book = save(book);
        return book;
    }

    // TODO - poniższa metoda ostatecznie do usunięcia - metoda pozwala na wykonanie szybkich testów ręcznych
    public static void main(String[] args) {
        Book book = getBookByIsbnFromWeb("9780262140874");
    }
}
