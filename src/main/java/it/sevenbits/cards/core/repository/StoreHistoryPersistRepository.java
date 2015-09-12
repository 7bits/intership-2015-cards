package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.StoreHistory;
import it.sevenbits.cards.core.mappers.StoreHistoryMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier(value = "storeHistoryPersistRepository")
public class StoreHistoryPersistRepository implements StoreHistoryRepository{
    private static Logger LOG = Logger.getLogger(StoreHistoryPersistRepository.class);
    @Autowired
    private StoreHistoryMapper storeHistoryMapper;

    @Override
    public List<StoreHistory> findAll(String email) throws RepositoryException {
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            return storeHistoryMapper.findAll(email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving history: " + e.getMessage(), e);
        }
    }
    @Override
    public void save(StoreHistory storeHistory, String email) throws RepositoryException{
        if (storeHistory == null) {
            throw new RepositoryException("StoreHistory is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try{
            storeHistoryMapper.save(storeHistory, email);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while saving history: " + e.getMessage(), e);
        }
    }
}
