package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.UseForm;

import java.util.List;

public interface DiscountsRepository {
    void save(final Discount discount) throws RepositoryException;
    List<Discount> findAllDiscounts() throws RepositoryException;
    void delete(final Discount discount) throws RepositoryException;
    List<Discount> findAllDiscountsToUse() throws RepositoryException;
    List<Discount> findAllDiscountsToSend() throws RepositoryException;
    List<Discount> findUserId(final Discount discount) throws RepositoryException;
    void changeUserId(final Discount discount) throws RepositoryException;
    void sendDiscount(final Discount discount) throws RepositoryException;
}

