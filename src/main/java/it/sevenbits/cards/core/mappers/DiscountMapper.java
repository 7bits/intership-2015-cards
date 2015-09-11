package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {

    //Save
    @Insert("INSERT INTO discounts (key, is_hidden, user_id, backer_user_id, campaign_id, deleted, hash, created_at)\n" +
            "VALUES (#{key}, #{isHidden}, #{userId}, #{backerUserId}, #{campaignId}, #{deleted}, #{hash}, #{createdAt})")
    void save(final Discount discount);

    //delete from discounts where key='BBBB' and
    // (select email from users
    // inner join stores
    // on users.id=stores.user_id
    // inner join campaigns
    // on campaigns.store_id = stores.id
    // where discounts.campaign_id = campaigns.id) = 'store';

    //Delete discount by key
    @Delete("DELETE FROM discounts where discounts.key = #{key} AND discount.is_hidden = false AND\n +" +
            "(SELECT users.email FROM users INNER JOIN stores ON users.id = stores.user_id\n" +
            "INNER JOIN campaigns on campaigns.store_id = stores.id\n" +
            "WHERE discounts.campaign_id = campaigns.id) = #{email}")
    void delete(@Param("key") String key, @Param("email") String email);

    //Find all discount with specified hidden status by email
    @Select("SELECT discounts.id, discounts.key, discounts.is_hidden, discounts.user_id, discounts.backer_user_id, discounts.campaign_id, discounts.deleted, discounts.hash, discounts.created_at\n" +
            "FROM discounts INNER JOIN users on discounts.email = users.email\n" +
            "WHERE discounts.is_hidden = #{isHidden} and users.email = #{email}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "backer_user_id", property = "backerUserId"),
            @Result(column = "campaign_id", property = "campaignId"),
            @Result(column = "deleted", property = "deleted"),
            @Result(column = "hash", property = "hash"),
            @Result(column = "created_at", property = "createdAt")
    })
    List<Discount> findAllWithSpecifiedHiddenStatusByEmail(@Param("isHidden") Boolean isHidden, @Param("email") String email);

    //ChangeUserId
    @Update("UPDATE discounts SET user_id = #{userId}, is_hidden = false WHERE uin = #{uin}")
    void changeUserId(@Param("uin") String uin, @Param("userId") String userId);

    //Send
    @Update("UPDATE discounts SET user_id = #{userId}, is_hidden = false, email=#{email} WHERE uin = #{uin}")
    void send(@Param("userId") String userId, @Param("uin") String uin, @Param("email") String email);

    //Find Discount Owner
    @Select("SELECT user_id\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Result(column = "user_id")
    String findDiscountOwner(@Param("uin") String uin);

    //Find Discount maker
    @Select("SELECT store_name\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Result(column = "store_name")
    String findDiscountMaker(@Param("key") String key);

    //Find Hidden Status By Key
    @Select("SELECT is_hidden\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Result(column = "is_hidden")
    Boolean findHiddenStatusByKey(@Param("key") String key);

    //Find Hidden Status By Uin
    @Select("SELECT is_hidden\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Result(column = "is_hidden")
    Boolean findHiddenStatusByUin(@Param("uin") String uin);

    //Find Discount Id By Key
    @Select("SELECT id\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Result(column = "id")
    Long findDiscountIdByKey(@Param("key") String key);

    //Find Discount Id By Uin
    @Select("SELECT id\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Result(column = "id")
    Long findDiscountIdByUin(@Param("uin") String uin);

    //Find Discount By Uin
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image, backer_percent, backer_user_id, email\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "backer_percent", property = "backerPercent"),
            @Result(column = "backer_user_id", property = "backerUserId"),
            @Result(column = "email", property = "email")
    })
    Discount findDiscountByUin(@Param("uin") String uin);

    //Find Discount By Id
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image, backer_percent, backer_user_id, email\n" +
            "FROM discounts\n" +
            "WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "backer_percent", property = "backerPercent"),
            @Result(column = "backer_user_id", property = "backerUserId"),
            @Result(column = "email", property = "email")
    })
    Discount findDiscountById(@Param("id") Long id);

    //Find Discount By Key
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image, backer_percent, backer_user_id, email\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "backer_percent", property = "backerPercent"),
            @Result(column = "backer_user_id", property = "backerUserId"),
            @Result(column = "email", property = "email")
    })
    Discount findDiscountByKey(@Param("key") String key);

    //Find Discount By Email
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image, backer_percent, backer_user_id, email\n" +
            "FROM discounts\n" +
            "WHERE email=#{email}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "backer_percent", property = "backerPercent"),
            @Result(column = "backer_user_id", property = "backerUserId"),
            @Result(column = "email", property = "email")
    })
    Discount findDiscountByEmail(@Param("email") String email);

    //Edit Exist Discount For User By His Email With Change Owner
    @Update("UPDATE discounts SET user_id = #{userId} WHERE email = #{email}")
    void addExistDiscountsByEmailFirst(@Param("email") String email, @Param("userId") String userId);

    //Edit Exist Discount For User By His Email With Change Backer
    @Update("UPDATE discounts SET backer_user_id = #{userId} WHERE email = #{email} AND backer_user_id=\'\'")
    void addExistDiscountsByEmailSecond(@Param("email") String email, @Param("userId") String userId);

}