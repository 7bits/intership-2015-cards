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
    public void saveByAcoustics(final Discount discount) throws RepositoryException {
        if (discount == null) {
            throw new RepositoryException("Discount is null");
        }
        try {
            mapper.saveByAcoustics(discount);
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
    public void send(String userId, String uin, String email) throws RepositoryException {
        if (userId == null) {
            throw new RepositoryException("userId is null");
        }
        if (uin == null) {
            throw new RepositoryException("Uin is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            mapper.send(userId, uin, email);
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
    @Override
    public String findDiscountMaker(String key) throws RepositoryException{
        if(key==null){
            throw new RepositoryException("Key is null");
        }
        try {
            return mapper.findDiscountMaker(key);
        }catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount maker: " + e.getMessage(), e);
        }
    }
    @Override
    public Boolean findHiddenStatusByKey(String key) throws RepositoryException{
        if(key==null){
            throw new RepositoryException("Key is null");
        }
        try {
            return mapper.findHiddenStatusByKey(key);
        }catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount hidden status by key: " + e.getMessage(), e);
        }
    }
    @Override
    public Boolean findHiddenStatusByUin(String uin) throws RepositoryException{
        if(uin==null){
            throw new RepositoryException("Uin is null");
        }
        try {
            return mapper.findHiddenStatusByUin(uin);
        }catch (Exception e){
            throw new RepositoryException("An error occurred while finding discount hidden status by uin: " + e.getMessage(), e);
        }
    }
    @Override
    public Long findDiscountIdByKey(String key) throws RepositoryException{
        if(key==null){
            throw new RepositoryException("Key is null");
        }
        try{
            return mapper.findDiscountIdByKey(key);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while finding discount id by key: " + e.getMessage(), e);
        }
    }
    @Override
    public Long findDiscountIdByUin(String uin) throws RepositoryException{
        if(uin==null){
            throw new RepositoryException("Uin is null");
        }
        try{
            return mapper.findDiscountIdByUin(uin);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while finding discount id by uin: " + e.getMessage(), e);
        }
    }

    @Override
    public Discount findDiscountByUin(String uin) throws RepositoryException {
        if(uin==null){
            throw new RepositoryException("Uin is null");
        }
        try{
            return mapper.findDiscountByUin(uin);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while finding discount by uin: " + e.getMessage(), e);
        }
    }
    @Override
    public Discount findDiscountById(Long id) throws RepositoryException {
        if(id==null){
            throw new RepositoryException("Id is null");
        }
        try{
            return mapper.findDiscountById(id);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while finding discount by id: " + e.getMessage(), e);
        }
    }
    @Override
    public Discount findDiscountByKey(String key) throws RepositoryException {
        if(key==null){
            throw new RepositoryException("Uin is null");
        }
        try{
            return mapper.findDiscountByKey(key);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while finding discount by key: " + e.getMessage(), e);
        }
    }
    @Override
    public void addExistDiscountsByEmail(String  email, String userId) throws RepositoryException{
        if(email==null){
            throw new RepositoryException("Email in null");
        }
        if(userId==null){
            throw new RepositoryException("UserId in null");
        }
        try{
            mapper.addExistDiscountsByEmailFirst(email, userId);
            mapper.addExistDiscountsByEmailSecond(email, userId);
        }catch(Exception e){
            throw new RepositoryException("An error occurred while adding discounts by email: " + e.getMessage(), e);
        }
    }
}