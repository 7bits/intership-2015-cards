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
    public List<Discount> findAll() throws RepositoryException {
        try {
            return mapper.findAll();
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
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
    public List<Discount> findAllForUse(String userName) throws RepositoryException {
        try {
            return mapper.findAllForUse(userName);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Discount> findAllForSend(String userName) throws RepositoryException {
        try {
            return mapper.findAllForSend(userName);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Discount> findUserId(Discount discount) throws RepositoryException {
        try {
            return mapper.findUserId(discount);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }

    @Override
    public void changeUserId(String uin, String userId) throws RepositoryException {
        if (uin == null) {
            throw new RepositoryException("Uin is null");
        }
        if (userId == null) {
            throw new RepositoryException("Discount is null");
        }
        try {
            mapper.changeUserId(uin, userId);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }
    @Override
    public void send(String userId, String uin) throws RepositoryException {
        if (userId == null) {
            throw new RepositoryException("userId is null");
        }
        if (uin == null) {
            throw new RepositoryException("Uin is null");
        }
        try {
            mapper.send(userId, uin);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while sending discount: " + e.getMessage(), e);
        }
    }
    @Override
    public String findDiscountOwner(String uin) throws RepositoryException{
        if (uin == null) {
            throw new RepositoryException("Uin is null");
        }
        try{
            return mapper.findDiscountOwner(uin);
        }catch (Exception e) {
            throw new RepositoryException("An error occurred while finding discount owner: " + e.getMessage(), e);
        }
    }
}