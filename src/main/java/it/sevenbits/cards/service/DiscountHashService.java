package it.sevenbits.cards.service;

import it.sevenbits.cards.core.repository.DiscountHashRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiscountHashService {

    @Autowired
    private DiscountHashRepository discountHashRepository;

    Logger LOG = Logger.getLogger(DiscountHashService.class);

    public void delete(String hash) throws ServiceException {
        try {
            discountHashRepository.delete(hash);
        } catch (Exception e) {
            throw new ServiceException("Find email by hash database error" + e.getMessage(), e);
        }
    }
    public Long findIdByHash(String hash) throws ServiceException{
        try{
            return discountHashRepository.findIdByHash(hash);
        }catch (Exception e) {
            throw new ServiceException("Find id by hash database error" + e.getMessage(), e);
        }
    }
}
