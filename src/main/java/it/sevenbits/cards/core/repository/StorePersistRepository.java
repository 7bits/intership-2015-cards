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
    public Store findByEmail(String email) throws RepositoryException{
        try {
            return mapper.findByEmail(email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while finding store by email: " + e.getMessage(), e);
        }
    }
}