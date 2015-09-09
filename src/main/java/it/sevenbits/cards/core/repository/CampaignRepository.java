package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Campaign;

import java.util.List;

public interface CampaignRepository {
    void save(final Campaign campaign) throws RepositoryException;
    List<Campaign> findAllWithEnabledStatus(Long storeId, Boolean enabled) throws RepositoryException;
    void changeEnableStatus(Long id) throws RepositoryException;
}