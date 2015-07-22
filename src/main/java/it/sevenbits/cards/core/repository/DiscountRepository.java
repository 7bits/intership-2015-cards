package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.UseForm;

import java.util.List;

public interface DiscountRepository {
    void save(final Discount discount) throws RepositoryException;
    List<Discount> findAll() throws RepositoryException;
    void delete(final Discount discount) throws RepositoryException;
    List<Discount> findAllForUse(String userName) throws RepositoryException;
    List<Discount> findAllForSend(String userName) throws RepositoryException;
    List<Discount> findUserId(final Discount discount) throws RepositoryException;
    void changeUserId(final Discount discount) throws RepositoryException;
    void send(String userId, String uin, String userName) throws RepositoryException;
}

