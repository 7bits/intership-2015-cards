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
    public List<StoreHistory> findAll(Long storeId) throws RepositoryException {
        if (storeId == null) {
            throw new RepositoryException("StoreId is null");
        }
        try {
            return storeHistoryMapper.findAll(storeId);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving history: " + e.getMessage(), e);
        }
    }
    @Override
    public void save(StoreHistory storeHistory) throws RepositoryException{
        if (storeHistory == null) {
            throw new RepositoryException("StoreHistory is null");
        }
        try{
            storeHistoryMapper.save(storeHistory);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while saving history: " + e.getMessage(), e);
        }
    }
}
