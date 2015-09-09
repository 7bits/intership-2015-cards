package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.StoreHistory;

import java.util.List;

public interface StoreHistoryRepository {
    List<StoreHistory> findAll(Long storeId) throws RepositoryException;
    void save(StoreHistory storeHistory) throws RepositoryException;
}
