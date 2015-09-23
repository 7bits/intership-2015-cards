package it.sevenbits.cards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by deamor on 22.09.15.
 */
@Service
public class MessageMapService extends ResourceBundleMessageSource {
    private Logger LOG = Logger.getLogger(MessageMapService.class);
    @Autowired
    MessageByLocaleService messageByLocaleService;
    public Map<String,String> getMessageMap(String bundle_basename) {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle bundle = getResourceBundle(bundle_basename, locale);
        List<String> keysList = Collections.list(bundle.getKeys());
        Map<String,String> mesmap = new HashMap<>();
        for(String key : keysList){
            mesmap.put(key, messageByLocaleService.getMessage(key));
        }
        return mesmap;

    }


}
