package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Store;

public interface StoreRepository {
    void save(final Store store) throws RepositoryException;
    void saveChanges(final Store store) throws RepositoryException;
    String findStoreNameByUserId(String userId) throws RepositoryException;
    Store findStoreByUserId(String userId) throws RepositoryException;
}

