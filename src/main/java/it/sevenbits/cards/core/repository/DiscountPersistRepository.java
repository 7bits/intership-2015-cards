package it.sevenbits.cards.core.repository;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.mappers.DiscountMapper;
import org.apache.catalina.startup.ClassLoaderFactory;
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
    private DiscountMapper mapper;
    @Override
    public void save(final Discount discount) throws RepositoryException {
        if (discount == null) {
            throw new RepositoryException("Discount is null");
        }
        try {
            mapper.save(discount);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String key, String storeName) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Key is null");
        }
        if (storeName == null) {
            throw new RepositoryException("StoreName is null");
        }
        try {
            mapper.delete(key, storeName);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Discount> findAllWithHiddenStatus(Boolean isHidden, String email) throws RepositoryException {
        try {
            return mapper.findAllWithHiddenStatus(isHidden, email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
}