package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Store;

public interface StoreRepository {
    void save(final Store store) throws RepositoryException;
}

