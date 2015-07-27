package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.UseForm;

import java.util.List;

public interface DiscountRepository {
    void save(final Discount discount) throws RepositoryException;
    List<Discount> findAll() throws RepositoryException;
    void delete(String key) throws RepositoryException;
    List<Discount> findAllForUse(String userName) throws RepositoryException;
    List<Discount> findAllForSend(String userName) throws RepositoryException;
    List<Discount> findUserId(final Discount discount) throws RepositoryException;
    void changeUserId(String uin, String userId) throws RepositoryException;
    void send(String email, String uin) throws RepositoryException;
}

