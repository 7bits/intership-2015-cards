package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.UseForm;

import java.util.List;

public interface DiscountRepository {
    void save(final Discount discount) throws RepositoryException;
    void saveByAcoustics(final Discount discount) throws RepositoryException;
    List<Discount> findAll() throws RepositoryException;
    void delete(String key, String storeName) throws RepositoryException;
    List<Discount> findAllForUse(String userName) throws RepositoryException;
    List<Discount> findAllForSend(String userName) throws RepositoryException;
    List<Discount> findUserId(final Discount discount) throws RepositoryException;
    void changeUserId(String uin, String userId) throws RepositoryException;
    void send(String userId, String uin, String email) throws RepositoryException;
    String findDiscountOwner(String uin) throws RepositoryException;
    String findDiscountMaker(String key) throws RepositoryException;
    Boolean findHiddenStatusByKey(String key) throws RepositoryException;
    Boolean findHiddenStatusByUin(String uin) throws RepositoryException;
    Long findDiscountIdByKey(String key) throws RepositoryException;
    Long findDiscountIdByUin(String uin) throws RepositoryException;
    Discount findDiscountByUin(String uin) throws RepositoryException;
    Discount findDiscountByKey(String key) throws RepositoryException;
    Discount findDiscountById(Long id) throws RepositoryException;
    Discount findDiscountByEmail(String email) throws RepositoryException;
    void addExistDiscountsByEmail(String email, String userId) throws RepositoryException;
}

