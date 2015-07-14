package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.web.domain.DiscountForm;
import it.sevenbits.cards.web.domain.UseForm;

import java.util.List;

public interface DiscountRepository {
    void save(final Discount discount) throws RepositoryException;
    List<Discount> findAll() throws RepositoryException;
    void delete(final Discount discount) throws RepositoryException;
    List<Discount> findAllDiscountsToUse() throws RepositoryException;
    List<Discount> findAllDiscountsToSend() throws RepositoryException;
}

