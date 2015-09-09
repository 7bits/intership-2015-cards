package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.StoreHistory;

import java.util.List;

public interface StoreHistoryRepository {
    List<StoreHistory> findAll(String email) throws RepositoryException;
    void save(StoreHistory storeHistory) throws RepositoryException;
}
