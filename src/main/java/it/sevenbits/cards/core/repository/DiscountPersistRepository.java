package it.sevenbits.cards.core.repository;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.mappers.DiscountMapper;
import it.sevenbits.cards.web.domain.forms.SendForm;
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
    public List<Discount> findAllWithHiddenStatus(Boolean isHidden, String email) throws RepositoryException {
        if (isHidden == null) {
            throw new RepositoryException("IsHidden is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            return discountMapper.findAllWithHiddenStatus(isHidden, email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while retrieving discounts: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Key is null");
        }
        try {
            discountMapper.delete(key);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while deleting discount: " + e.getMessage(), e);
        }
    }

    @Override
    public void createByCampaign(String key, String email, Long campaignId, String hash) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Key is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        if (campaignId == null) {
            throw new RepositoryException("CampaignId is null");
        }
        if(hash == null){
            throw new RepositoryException("Hash is null");
        }
        try {
            discountMapper.createByCampaign(key, email, campaignId, hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving discount: " + e.getMessage(), e);
        }
    }

    @Override
    public void changeDiscountOwner(String email, String hash) throws RepositoryException{
        if(email == null) {
            throw new RepositoryException("Email is null");
        }
        if(hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            discountMapper.changeDiscountOwner(email, hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while changing discount owner: " + e.getMessage(), e);
        }
    }

    @Override
    public void changeDiscountOwnerByForm(String email, String key, String hash) throws RepositoryException{
        if(email == null) {
            throw new RepositoryException("Email is null");
        }
        if(key == null) {
            throw new RepositoryException("Key is null");
        }
        if(hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            discountMapper.changeDiscountOwnerByForm(email, key, hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while changing discount owner: " + e.getMessage(), e);
        }
    }

    @Override
    public Discount findDiscountByHash(String hash) throws RepositoryException{
        if(hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            return discountMapper.findDiscountByHash(hash);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount by hash: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteHash(String hash) throws RepositoryException{
        if(hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            discountMapper.deleteHash(hash);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while deleting hash by hash: " + e.getMessage(), e);
        }
    }

    @Override
    public Long findDiscountIdByEmailAndKey(String email, String key) throws RepositoryException{
        if(email == null) {
            throw new RepositoryException("Email is null");
        }
        if(key == null) {
            throw new RepositoryException("Key is null");
        }
        try{
            return discountMapper.findDiscountIdByEmailAndKey(email, key);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount id by email and key: " + e.getMessage(), e);
        }
    }

    @Override
    public Long findDiscountIdByMakerEmailAndKey(String email, String key) throws RepositoryException{
        if(email == null) {
            throw new RepositoryException("Email is null");
        }
        if(key == null) {
            throw new RepositoryException("Key is null");
        }
        try{
            return discountMapper.findDiscountIdByMakerEmailAndKey(email, key);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount id by maker email and key: " + e.getMessage(), e);
        }
    }
    @Override
    public Boolean findDiscountHiddenStatusByKey(String key)throws RepositoryException{
        if(key == null) {
            throw new RepositoryException("Key is null");
        }
        try{
            return discountMapper.findDiscountHiddenStatusByKey(key);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount hidden status by key: " + e.getMessage(), e);
        }
    }

    @Override
    public Long findIdByHash(String hash)throws RepositoryException{
        if(hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            return discountMapper.findIdByHash(hash);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount id by hash: " + e.getMessage(), e);
        }
    }

    @Override
    public Discount findDiscountByKey(String key) throws RepositoryException{
        if(key == null) {
            throw new RepositoryException("Key is null");
        }
        try{
            return discountMapper.findDiscountByKey(key);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount by key: " + e.getMessage(), e);
        }
    }

    @Override
    public void createFeedbackDiscountAfterUse(Discount discount) throws RepositoryException{
        if(discount == null){
            throw new RepositoryException("Discount is null");
        }
        try{
            discountMapper.createFeedbackDiscountAfterUse(discount);
        } catch (Exception e){
            throw new RepositoryException("An error occurred while creating discount after use: " + e.getMessage(), e);
        }
    }
}