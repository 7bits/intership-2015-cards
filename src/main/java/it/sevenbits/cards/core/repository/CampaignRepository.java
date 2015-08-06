package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Campaign;

public interface CampaignRepository {
    void save(final Campaign campaign) throws RepositoryException;
}