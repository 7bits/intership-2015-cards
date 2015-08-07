package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.web.domain.CampaignModel;

import java.util.List;

public interface CampaignRepository {
    void save(final Campaign campaign) throws RepositoryException;

    List<Campaign> findAllActive(String storeName) throws RepositoryException;
    List<Campaign> findAllNotActive(String storeName) throws RepositoryException;
}