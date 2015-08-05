package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.web.controllers.HomeController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
@Service
public class GenerateKey {

    @Autowired
    @Qualifier(value = "discountPersistRepository")
    private DiscountRepository repository;

    private Logger LOG = Logger.getLogger(GenerateKey.class);
    public String random() {
        Boolean codeExist = false;
        String result = "";
        do {
            codeExist = false;
            result="";
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                char temp = chars.charAt(random.nextInt(chars.length()));
                result += temp;
            }
            Long discountId;
            try{
                discountId = repository.findDiscountIdByKey(result);
            }catch (Exception e){
                discountId = null;
            }
            if(discountId!=null){
                codeExist = true;
            }
        }while(codeExist);
        return result;
    }
}
