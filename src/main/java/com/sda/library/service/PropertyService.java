package com.sda.library.service;

import com.sda.library.model.Property;
import com.sda.library.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    // default values
    final Integer defReservationDaysLimit = 3;
    final Integer defDaysLimitBookReturn = 90;
    final Boolean defSendEmailPossibleBorrowNotification = true;
    final Boolean defSendBookReturnReminder = true;
    final String defMailUsername = "eLibrarySDA@gmail.com";
    final String defMailPassword = "sdaJAVAbyd2@)!*";
    final String defMailAccountName = "eLibrary";
    final Boolean defMailSmtpAuth = true;
    final Boolean defMailSmtpStarttlsEnable = true;
    final String defMailSmtpHost = "smtp.gmail.com";
    final Integer defMailSmtpPort = 587;
    final String defBarCodeAppIP = "";
    final Integer defBarCodeAppPort = 8081;//(?)

    @Autowired
    public PropertyService(final PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    public Integer getReservationDaysLimit(){
        Optional<Property> daysLimitBookReturn = propertyRepository.findById("daysLimitBookReturn");
        if (daysLimitBookReturn.isPresent()) {
            return daysLimitBookReturn.get().getValueInt();
        } else {
            Property property = new Property();
            property.setKeyName("reservationDaysLimit");
            property.setValueInt(defReservationDaysLimit);
            propertyRepository.save(property);
            return defReservationDaysLimit;
        }
    }

    public Integer getDaysLimitBookReturn(){
        Optional<Property> daysLimitBookReturn = propertyRepository.findById("daysLimitBookReturn");
        if (daysLimitBookReturn.isPresent()) {
            return daysLimitBookReturn.get().getValueInt();
        } else {
            Property property = new Property();
            property.setKeyName("daysLimitBookReturn");
            property.setValueInt(defDaysLimitBookReturn);
            propertyRepository.save(property);
            return defDaysLimitBookReturn;
        }
    }

    public Boolean getSendEmailPossibleBorrowNotification(){
        Optional<Property> sendEmailPossibleBorrowNotification = propertyRepository.findById("sendEmailPossibleBorrowNotification");
        if (sendEmailPossibleBorrowNotification.isPresent()) {
            return sendEmailPossibleBorrowNotification.get().getValueBool();
        } else {
            Property property = new Property();
            property.setKeyName("sendEmailPossibleBorrowNotification");
            property.setValueBool(defSendEmailPossibleBorrowNotification);
            propertyRepository.save(property);
            return defSendEmailPossibleBorrowNotification;
        }
    }

    public Boolean getSendBookReturnReminder(){
        Optional<Property> sendBookReturnReminder = propertyRepository.findById("sendBookReturnReminder");
        if (sendBookReturnReminder.isPresent()) {
            return sendBookReturnReminder.get().getValueBool();
        } else {
            Property property = new Property();
            property.setKeyName("sendBookReturnReminder");
            property.setValueBool(defSendBookReturnReminder);
            propertyRepository.save(property);
            return defSendBookReturnReminder;
        }
    }

    public String getMailUsername(){
        Optional<Property> mailUsername = propertyRepository.findById("mailUsername");
        if (mailUsername.isPresent()) {
            return mailUsername.get().getValueStr();
        } else {
            Property property = new Property();
            property.setKeyName("mailUsername");
            property.setValueStr(defMailUsername);
            propertyRepository.save(property);
            return defMailUsername;
        }
    }

    public String getMailPassword(){
        Optional<Property> mailPassword = propertyRepository.findById("mailPassword");
        if (mailPassword.isPresent()) {
            return mailPassword.get().getValueStr();
        } else {
            Property property = new Property();
            property.setKeyName("mailPassword");
            property.setValueStr(defMailPassword);
            propertyRepository.save(property);
            return defMailPassword;
        }
    }

    public String getMailAccountName(){
        Optional<Property> mailAccountName = propertyRepository.findById("mailAccountName");
        if (mailAccountName.isPresent()) {
            return mailAccountName.get().getValueStr();
        } else {
            Property property = new Property();
            property.setKeyName("mailAccountName");
            property.setValueStr(defMailAccountName);
            propertyRepository.save(property);
            return defMailAccountName;
        }
    }

    public Boolean getMailSmtpAuth(){
        Optional<Property> mailSmtpAuth = propertyRepository.findById("mailSmtpAuth");
        if (mailSmtpAuth.isPresent()) {
            return mailSmtpAuth.get().getValueBool();
        } else {
            Property property = new Property();
            property.setKeyName("mailSmtpAuth");
            property.setValueBool(defMailSmtpAuth);
            propertyRepository.save(property);
            return defMailSmtpAuth;
        }
    }

    public Boolean getMailSmtpStarttlsEnable(){
        Optional<Property> mailSmtpStarttlsEnable = propertyRepository.findById("mailSmtpStarttlsEnable");
        if (mailSmtpStarttlsEnable.isPresent()) {
            return mailSmtpStarttlsEnable.get().getValueBool();
        } else {
            Property property = new Property();
            property.setKeyName("mailSmtpStarttlsEnable");
            property.setValueBool(defMailSmtpStarttlsEnable);
            propertyRepository.save(property);
            return defMailSmtpStarttlsEnable;
        }
    }

    public String getMailSmtpHost(){
        Optional<Property> mailSmtpHost = propertyRepository.findById("mailSmtpHost");
        if (mailSmtpHost.isPresent()) {
            return mailSmtpHost.get().getValueStr();
        } else {
            Property property = new Property();
            property.setKeyName("mailSmtpHost");
            property.setValueStr(defMailSmtpHost);
            propertyRepository.save(property);
            return defMailSmtpHost;
        }
    }

    public Integer getMailSmtpPort(){
        Optional<Property> mailSmtpPort = propertyRepository.findById("mailSmtpPort");
        if (mailSmtpPort.isPresent()) {
            return mailSmtpPort.get().getValueInt();
        } else {
            Property property = new Property();
            property.setKeyName("mailSmtpPort");
            property.setValueInt(defMailSmtpPort);
            propertyRepository.save(property);
            return defMailSmtpPort;
        }
    }

    public String getBarCodeAppIP(){
        Optional<Property> barCodeAppIP = propertyRepository.findById("barCodeAppIP");
        if (barCodeAppIP.isPresent()) {
            return barCodeAppIP.get().getValueStr();
        } else {
            Property property = new Property();
            property.setKeyName("barCodeAppIP");
            property.setValueStr(defBarCodeAppIP);
            propertyRepository.save(property);
            return defBarCodeAppIP;
        }
    }

    public Integer getBarCodeAppPort(){
        Optional<Property> barCodeAppPort = propertyRepository.findById("barCodeAppPort");
        if (barCodeAppPort.isPresent()) {
            return barCodeAppPort.get().getValueInt();
        } else {
            Property property = new Property();
            property.setKeyName("barCodeAppPort");
            property.setValueInt(defBarCodeAppPort);
            propertyRepository.save(property);
            return defBarCodeAppPort;
        }
    }
}
