package it.sevenbits.cards.core.repository;
import it.sevenbits.cards.core.domain.Store;
import it.sevenbits.cards.core.mappers.StoreMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "storePersistRepository")
public class StorePersistRepository implements StoreRepository {
    private static Logger LOG = Logger.getLogger(StorePersistRepository.class);
    @Autowired
    private StoreMapper mapper;
    @Override
    public void save(final Store store) throws RepositoryException {
        if (store == null) {
            throw new RepositoryException("Store is null");
        }
        try {
            mapper.save(store);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving store: " + e.getMessage(), e);
        }
    }

    @Override
    public Store findStoreByUserId(String userId) throws RepositoryException{
        try {
            return mapper.findStoreByUserId(userId);
        } catch (Exception e) {
            throw new RepositoryException("findStoreByUserId error: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveChanges(final Store store) throws RepositoryException{
        //Validation. Check null
        try {
            mapper.updateDescribe(store.getUserId(), store.getDescribe());
            mapper.updateDiscount(store.getUserId(), store.getDiscount());
        } catch (Exception e) {
            throw new RepositoryException("saveChanges() repository error: " + e.getMessage(), e);
        }
    }

    @Override
    public String findStoreNameByUserId(final String userId) throws RepositoryException {
        if (userId == null) {
            throw new RepositoryException("UserId is null");
        }
        try {
            return mapper.findStoreNameByUserId(userId);
        } catch (Exception e) {
            throw new RepositoryException("General database error " + e.getMessage(), e);
        }
    }
}