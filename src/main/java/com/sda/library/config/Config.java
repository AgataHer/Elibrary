package com.sda.library.config;

import com.sda.library.model.Property;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// TODO zmienić na obsługę pliku konfiguracyjnego

public class Config {
    private static final SessionFactory sessionFactory;
    private static List<Property> properties = new ArrayList<>();

    public static Integer reservationDaysLimit = 0;
    public static Integer daysLimitBookReturn = 0;
    public static Boolean sendEmailPossibleBorrowNotification = false;
    public static Boolean sendBookReturnReminder = false;
    public static String mailUsername = "";
    public static String mailPassword = "";
    public static String mailAccountName = "";
    public static Boolean mailSmtpAuth = false;
    public static Boolean mailSmtpStarttlsEnable = false;
    public static String mailSmtpHost = "";
    public static Integer mailSmtpPort = 0;
    public static String barCodeAppIP = "";
    public static Integer barCodeAppPort = 0;

    // default values
    final static private Integer defReservationDaysLimit = 3;
    final static private Integer defDaysLimitBookReturn = 90;
    final static private Boolean defSendEmailPossibleBorrowNotification = true;
    final static private Boolean defSendBookReturnReminder = true;
    final static private String defMailUsername = "eLibrarySDA@gmail.com";
    final static private String defMailPassword = "sdaJAVAbyd2@)!*";
    final static private String defMailAccountName = "eLibrary";
    final static private Boolean defMailSmtpAuth = true;
    final static private Boolean defMailSmtpStarttlsEnable = true;
    final static private String defMailSmtpHost = "smtp.gmail.com";
    final static private Integer defMailSmtpPort = 587;
    final static private String defBarCodeAppIP = "";
    final static private Integer defBarCodeAppPort = 8081;//(?)

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    public static void loadProperties() {
        Session session = getSession();

        Query query = session.createQuery("from " + Property.class.getName());
        properties = query.getResultList();

        // gathering all properties and put default variables if missing
        reservationDaysLimit = (Integer) loadProperty(session, "reservationDaysLimit", reservationDaysLimit, defReservationDaysLimit);
        daysLimitBookReturn = (Integer) loadProperty(session, "daysLimitBookReturn", daysLimitBookReturn, defDaysLimitBookReturn);
        sendEmailPossibleBorrowNotification = (Boolean) loadProperty(session, "sendEmailPossibleBorrowNotification", sendEmailPossibleBorrowNotification, defSendEmailPossibleBorrowNotification);
        sendBookReturnReminder = (Boolean) loadProperty(session, "sendBookReturnReminder", sendBookReturnReminder, defSendBookReturnReminder);
        mailUsername = (String) loadProperty(session, "mailUsername", mailUsername, defMailUsername);
        mailPassword = (String) loadProperty(session, "mailPassword", mailPassword, defMailPassword);
        mailAccountName = (String) loadProperty(session, "mailAccountName", mailAccountName, defMailAccountName);
        mailSmtpAuth = (Boolean) loadProperty(session, "mailSmtpAuth", mailSmtpAuth, defMailSmtpAuth);
        mailSmtpStarttlsEnable = (Boolean) loadProperty(session, "mailSmtpStarttlsEnable", mailSmtpStarttlsEnable, defMailSmtpStarttlsEnable);
        mailSmtpHost = (String) loadProperty(session, "mailSmtpHost", mailSmtpHost, defMailSmtpHost);
        mailSmtpPort = (Integer) loadProperty(session, "mailSmtpPort", mailSmtpPort, defMailSmtpPort);
        barCodeAppIP = (String) loadProperty(session, "barCodeAppIP", barCodeAppIP, defBarCodeAppIP);
        barCodeAppPort = (Integer) loadProperty(session, "barCodeAppPort", barCodeAppPort, defBarCodeAppPort);

        session.getSessionFactory().close();
    }

    private static Object loadProperty(Session session, String name, Object object, Object defValue) {
        Optional<Object> objectOptional = Optional.empty();
        if (object instanceof String) {objectOptional = getValue(name, new String(""));}
        if (object instanceof Integer) {objectOptional = getValue(name, new Integer(0));}
        if (object instanceof Double) {objectOptional = getValue(name, new Double(0.0));}
        if (object instanceof Boolean) {objectOptional = getValue(name, new Boolean(false));}
        if (objectOptional.isPresent()) {
            if (objectOptional.get() instanceof String) {
                object = (String) objectOptional.get();
            }
            if (objectOptional.get() instanceof Integer) {
                object = (Integer) objectOptional.get();
            }
            if (objectOptional.get() instanceof Double) {
                object = (Double) objectOptional.get();
            }
            if (objectOptional.get() instanceof Boolean) {
                object = (Boolean) objectOptional.get();
            }
        } else {
            // save default value
            String defString = null;
            Integer defInteger = null;
            Double defDouble = null;
            Boolean defBoolean = null;
            if (defValue instanceof String) {
                defString = (String) defValue;
                object = (String) defValue;
            }
            if (defValue instanceof Integer) {
                defInteger = (Integer) defValue;
                object = (Integer) defValue;
            }
            if (defValue instanceof Double) {
                defDouble = (Double) defValue;
                object = (Double) defValue;
            }
            if (defValue instanceof Boolean) {
                defBoolean = (Boolean) defValue;
                object = (Boolean) defValue;
            }
            insertValue(session, new Property(name, defString, defInteger, defDouble, defBoolean));

        }
        return object;
    }

    private static void insertValue(Session session, Property property) {
        Transaction transaction = null;
        transaction = session.beginTransaction();
        session.save(property);
        transaction.commit();
    }

    private static Optional<Object> getValue(String name, Object type){
        Object object = null;
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            if (property.getKeyName().equals(name)) {
                if (type instanceof String) {
                    object = new String(property.getValueStr());
                } else if (type instanceof Integer) {
                    object = new Integer(property.getValueInt());
                } else if (type instanceof Double) {
                    object = new Double(property.getValueDbl());
                } else if (type instanceof Boolean) {
                    object = new Boolean(property.getValueBool());
                }
            }
        }
        return Optional.ofNullable(object);
    }
}
