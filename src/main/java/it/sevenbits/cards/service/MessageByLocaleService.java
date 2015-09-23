package it.sevenbits.cards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by deamor on 22.09.15.
 */
@Service
public class MessageByLocaleService {
    @Autowired
    MessageSource messageSource;


    public String getMessage(String id){

        Locale locale = LocaleContextHolder.getLocale();
        String temp = messageSource.getMessage(id, null, locale);
        byte[] arr = null;
        String newstr = null;
        try {
            arr = temp.getBytes("ISO-8859-1");
            newstr = new String(arr,"UTF-8");
        }
        catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }

        return newstr;
    }
}
