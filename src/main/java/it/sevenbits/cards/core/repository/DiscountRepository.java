package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.web.domain.forms.SendForm;

import java.util.List;

public interface DiscountRepository {
    List<Discount> findAllWithHiddenStatus(Boolean isHidden, String email) throws RepositoryException;

    void delete(String key) throws RepositoryException;

    void createByCampaign(String key, String email, Long campaignId, String hash) throws RepositoryException;

    void changeDiscountOwner(String email, String hash) throws RepositoryException;

    void changeDiscountOwnerByForm(String email, String key, String hash) throws RepositoryException;

    Discount findDiscountByHash(String hash) throws RepositoryException;

    void deleteHash(String hash) throws RepositoryException;

    Long findDiscountIdByEmailAndKey(String email, String key) throws RepositoryException;

    Long findDiscountIdByMakerEmailAndKey(String email, String key) throws RepositoryException;

    Boolean findDiscountHiddenStatusByKey(String key) throws RepositoryException;

    Long findIdByHash(String hash) throws RepositoryException;

    Discount findDiscountByKey(String key) throws RepositoryException;

    void createFeedbackDiscountAfterUse(Discount discount) throws RepositoryException;
}

