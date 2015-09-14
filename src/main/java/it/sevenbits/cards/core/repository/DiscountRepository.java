package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;

import java.util.List;

public interface DiscountRepository {
    void createByCampaign(String key, String email, Long campaignId) throws RepositoryException;
    void delete(String key, String email) throws RepositoryException;
    List<Discount> findAllWithHiddenStatus(Boolean isHidden, String email) throws RepositoryException;
}

