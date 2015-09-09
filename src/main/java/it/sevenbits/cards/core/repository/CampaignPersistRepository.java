package it.sevenbits.cards.core.repository;
import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.mappers.CampaignMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier(value = "campaignPersistRepository")
public class CampaignPersistRepository implements CampaignRepository {
    private static Logger LOG = Logger.getLogger(CampaignPersistRepository.class);

    @Autowired
    private CampaignMapper campaignMapper;

    @Override
    public void save(final Campaign campaign) throws RepositoryException {
        if (campaign == null) {
            throw new RepositoryException("Campaign is null");
        }
        try {
            campaignMapper.save(campaign);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving campaign: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Campaign> findAllWithEnabledStatus(Long storeId, Boolean enabled) throws RepositoryException {
        if (storeId == null) {
            throw new RepositoryException("StoreId is null");
        }
        if (enabled == null) {
            throw new RepositoryException("Enabled is null");
        }
        try {
            return campaignMapper.findAllWithEnabledStatus(storeId, enabled);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while finding campaign with enabled status: " + e.getMessage(), e);
        }
    }

    @Override
    public void changeEnableStatus(Long id) throws RepositoryException{
        if(id == null) {
            throw new RepositoryException("Id in null");
        }
        try{
            campaignMapper.changeEnableStatus(id);
        }catch (Exception e) {
            throw new RepositoryException("An error occurred while changing campaign status: " + e.getMessage(), e);
        }
    }
}