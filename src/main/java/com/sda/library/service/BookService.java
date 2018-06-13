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
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

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
//TODO poniższe dwie linie służą do testowania ręcznego parsowania (podczas testów trzeba zakomentować powyższą linię) - ostatecznie do usunięcia
//            String jsonTemp = "{\"totalItems\":1,\"kind\":\"books#volumes\",\"items\":[{\"saleInfo\":{\"country\":\"PL\",\"isEbook\":false,\"saleability\":\"NOT_FOR_SALE\"},\"searchInfo\":{\"textSnippet\":\"This title gives students an integrated and rigorous picture of applied computer science, as it comes to play in the construction of a simple yet powerful computer system.\"},\"kind\":\"books#volume\",\"volumeInfo\":{\"industryIdentifiers\":[{\"identifier\":\"026214087X\",\"type\":\"ISBN_10\"},{\"identifier\":\"9780262140874\",\"type\":\"ISBN_13\"}],\"pageCount\":325,\"printType\":\"BOOK\",\"readingModes\":{\"image\":true,\"text\":false},\"previewLink\":\"http://books.google.pl/books?id=THie6tt-2z8C&printsec=frontcover&dq=isbn:9780262140874&hl=&cd=1&source=gbs_api\",\"canonicalVolumeLink\":\"https://books.google.com/books/about/The_Elements_of_Computing_Systems.html?hl=&id=THie6tt-2z8C\",\"description\":\"This title gives students an integrated and rigorous picture of applied computer science, as it comes to play in the construction of a simple yet powerful computer system.\",\"language\":\"en\",\"title\":\"The Elements of Computing Systems\",\"imageLinks\":{\"thumbnail\":\"http://books.google.com/books/content?id=THie6tt-2z8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=THie6tt-2z8C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"},\"subtitle\":\"Building a Modern Computer from First Principles\",\"averageRating\":5,\"publisher\":\"MIT Press\",\"ratingsCount\":8,\"publishedDate\":\"2005\",\"categories\":[\"Computers\"],\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":false,\"contentVersion\":\"1.0.0.0.preview.1\",\"authors\":[\"Noam Nisan\",\"Shimon Schocken\"],\"infoLink\":\"http://books.google.pl/books?id=THie6tt-2z8C&dq=isbn:9780262140874&hl=&source=gbs_api\"},\"etag\":\"LWiL4RZZMnI\",\"id\":\"THie6tt-2z8C\",\"accessInfo\":{\"accessViewStatus\":\"SAMPLE\",\"country\":\"PL\",\"viewability\":\"PARTIAL\",\"pdf\":{\"isAvailable\":false},\"webReaderLink\":\"http://play.google.com/books/reader?id=THie6tt-2z8C&hl=&printsec=frontcover&source=gbs_api\",\"epub\":{\"isAvailable\":false},\"publicDomain\":false,\"quoteSharingAllowed\":false,\"embeddable\":true,\"textToSpeechPermission\":\"ALLOWED\"},\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/THie6tt-2z8C\"}]}";
//            JSONObject jsonObject = new JSONObject(jsonTemp);
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
                            if (!selfLink.equals(null)) {
                                book.setSelfLink(selfLink);
                            }

                            String itemSaleInfo = itemObject.optString("saleInfo");
                            JSONObject jsonSaleInfo = new JSONObject(itemSaleInfo);
                            String itemCountry = jsonSaleInfo.optString("country");
                            if (!itemCountry.equals(null)) {
                                book.setLanguage(itemCountry);
                            }

                            String itemVolumeInfo = itemObject.optString("volumeInfo");
                            JSONObject jsonVolumeInfo = new JSONObject(itemVolumeInfo);
                            String itemTitle = jsonVolumeInfo.optString("title");
                            if (!itemTitle.equals(null)) {
                                book.setTitle(itemTitle);
                            }

                            String itemSubtitle = jsonVolumeInfo.optString("subtitle");
                            if (!itemSubtitle.equals(null)) {
                                book.setSubtitle(itemSubtitle);
                            }

                            String itemPublishedDate = jsonVolumeInfo.optString("publishedDate");
                            if (!itemPublishedDate.equals(null)) {
                                book.setPublishedDate(Integer.parseInt(itemPublishedDate));
                            }

                            String itemPageCount = jsonVolumeInfo.optString("pageCount");
                            if (!itemPageCount.equals(null)) {
                                book.setPageCount(Integer.parseInt(itemPageCount));
                            }

                            String itemDescription = jsonVolumeInfo.optString("description");
                            if (!itemDescription.equals(null)) {
                                book.setDescription(itemDescription);
                            }

                            JSONArray itemAuthors = jsonVolumeInfo.optJSONArray("authors");
                            if (!itemDescription.equals(null)) {
                                Set<Author> itemAuthorsSet = new HashSet<>();
                                for (int k = 0; k < itemAuthors.length(); k++) {
                                    itemAuthorsSet.add(new Author(itemAuthors.getString(k), null));
                                }
                                book.setAuthors(itemAuthorsSet);
                            }

                            JSONArray itemCategories = jsonVolumeInfo.optJSONArray("categories");
                            if (!itemCategories.equals(null)) {
                                Set<Category> itemCategoriesSet = new HashSet<>();
                                for (int k = 0; k < itemCategories.length(); k++) {
                                    Category category = new Category();
                                    category.setName(itemCategories.getString(k));
                                    itemCategoriesSet.add(category);
                                }
                                book.setCategories(itemCategoriesSet);
                            }

                            String itemPublisher = jsonVolumeInfo.optString("publisher");
                            if (!itemPublisher.equals(null)) {
                                Publisher publisher = new Publisher();
                                publisher.setName(itemPublisher);
                                book.setPublisher(publisher);
                            }

                            Iterator<?> keysII = jsonVolumeInfo.keys();
                            while (keysII.hasNext()) {
                                String keyII = (String) keysII.next();
                                String jsonStringII = jsonVolumeInfo.optString(keyII);
                                if (keyII.equals("industryIdentifiers")) {
                                    JSONArray itemsII = jsonVolumeInfo.getJSONArray("industryIdentifiers");
                                    for (int k = 0; k < itemsII.length(); k++) {
                                        JSONObject itemsIIObject = (JSONObject) itemsII.get(k);
                                        String itemsIIType = itemsIIObject.optString("type");
                                        String itemsIIIdentifier = itemsIIObject.optString("identifier");
                                        if (!itemsIIType.equals(null) && !itemsIIIdentifier.equals(null)) {
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
                                    if (!thumbnail.equals(null)) {
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


    // TODO - poniższa metoda ostatecznie do usunięcia - metoda pozwala na wykonanie szybkich testów ręcznych
    public static void main(String[] args) {
        Book book = getBookByIsbnFromWeb("9780262140874");
    }
}
