package it.sevenbits.cards.core.repository;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.mappers.DiscountMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
@Qualifier(value = "discountPersistRepository")
public class DiscountPersistRepository implements DiscountRepository {

    private static Logger LOG = Logger.getLogger(DiscountPersistRepository.class);

    @Autowired
    private DiscountMapper discountMapper;

    @Override
    public void createByCampaign(String key, String email, Long campaignId) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Key is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        if (campaignId == null) {
            throw new RepositoryException("CampaignId is null");
        }
        try {
            discountMapper.createByCampaign(key, email, campaignId);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String key, String email) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Key is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            discountMapper.delete(key, email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAllWithHiddenStatus(Boolean isHidden, String email) throws RepositoryException {
        try {
            return discountMapper.findAllWithHiddenStatus(isHidden, email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
}