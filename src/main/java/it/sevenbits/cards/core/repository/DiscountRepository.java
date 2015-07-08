package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;

import java.util.List;

public interface DiscountRepository {
    void save(final Discount discount) throws RepositoryException;
    List<Discount> findAll() throws RepositoryException;
}

