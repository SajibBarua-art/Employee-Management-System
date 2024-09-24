package net.javaguides.Employee_Management_System.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageSource messageSource;

//    @Autowired
//    private MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, null);
    }
}

