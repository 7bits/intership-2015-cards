package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;

import java.util.List;

public interface DiscountRepository {
    void save(final Discount discount) throws RepositoryException;
    void delete(String key, String storeName) throws RepositoryException;
    List<Discount> findAllWithHiddenStatus(Boolean isHidden, String email) throws RepositoryException;
}

