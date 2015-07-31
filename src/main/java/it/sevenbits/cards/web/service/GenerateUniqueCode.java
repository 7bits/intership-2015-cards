package it.sevenbits.cards.web.service;

import it.sevenbits.cards.web.controllers.HomeController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateUniqueCode {
    private Logger LOG = Logger.getLogger(HomeController.class);
    public String random() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 8; i++){
            char temp = chars.charAt(random.nextInt(chars.length()));
           result+=temp;
        }
        return result;
    }
}
