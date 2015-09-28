package it.sevenbits.cards.service;

import it.sevenbits.cards.core.repository.DiscountRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class GenerateKeyService {

    @Autowired
    private DiscountService discountService;

    private Logger LOG = Logger.getLogger(GenerateKeyService.class);
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
            Boolean isHidden;
            try{
                isHidden = discountService.findDiscountHiddenStatusByKey(result);
            }catch (Exception e){
                isHidden = null;
            }
            if(isHidden!=null){
                codeExist = true;
            }
        }while(codeExist);
        return result;
    }
}
