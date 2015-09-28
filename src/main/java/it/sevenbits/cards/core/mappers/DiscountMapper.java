package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.web.domain.forms.SendForm;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {

    //Save
    @Insert("INSERT INTO discounts (key, user_id, backer_user_id, campaign_id, hash)\n" +
            "VALUES (#{key}, (select id from users where email = #{email}), (select id from users where email = #{email}), #{campaignId}, #{hash})")
    void createByCampaign(@Param("key") String key, @Param("email") String email, @Param("campaignId") Long campaignId, @Param("hash") String hash);

    //Delete discount by key
    @Update("UPDATE discounts SET deleted = true, key = '' WHERE key = #{key} AND is_hidden = false")
    void delete(@Param("key") String key);

    //Find all discounts with hidden or not hidden status
    @Select("SELECT discounts.id, discounts.key, discounts.is_hidden, discounts.user_id, discounts.backer_user_id, discounts.campaign_id, discounts.deleted, discounts.hash, discounts.created_at,\n" +
            "campaigns.name, campaigns.description, campaigns.percent, campaigns.backer_percent,\n" +
            "stores.name, stores.image\n" +
            "FROM discounts INNER JOIN campaigns ON discounts.campaign_id = campaigns.id\n" +
            "INNER JOIN stores on campaigns.store_id = stores.id\n" +
            "INNER JOIN users on discounts.user_id = users.id\n" +
            "WHERE users.email = #{email} AND discounts.is_hidden = #{isHidden} AND discounts.deleted = false")
    @Results({
            @Result(column = "discounts.id", property = "id"),
            @Result(column = "discounts.key", property = "key"),
            @Result(column = "discounts.is_hidden", property = "isHidden"),
            @Result(column = "discounts.user_id", property = "userId"),
            @Result(column = "discounts.backer_user_id", property = "backerUserId"),
            @Result(column = "discounts.campaign_id", property = "campaignId"),
            @Result(column = "discounts.deleted", property = "deleted"),
            @Result(column = "discounts.hash", property = "hash"),
            @Result(column = "discounts.created_at", property = "createdAt"),

            @Result(column = "campaigns.name", property = "campaignName"),
            @Result(column = "campaigns.description", property = "description"),
            @Result(column = "campaigns.percent", property = "percent"),
            @Result(column = "campaigns.backer_percent", property = "backerPercent"),

            @Result(column = "stores.name", property = "storeName"),
            @Result(column = "stores.image", property = "storeImage")
    })
    List<Discount> findAllWithHiddenStatus(@Param("isHidden") Boolean isHidden, @Param("email") String email);

    //Change discount owner
    @Update("UPDATE discounts SET user_id = users.id, hash = '', is_hidden = false FROM users\n" +
            "WHERE users.email = #{email}  AND hash = #{hash}")
    void changeDiscountOwner(@Param("email") String email, @Param("hash") String hash);

    //Change discount owner
    @Update("UPDATE discounts SET user_id = users.id, is_hidden = false, hash = #{hash} FROM users\n" +
            "WHERE users.email = #{email}  AND key = #{key}")
    void changeDiscountOwnerByForm(@Param("email") String email, @Param("key") String key, @Param("hash") String hash);

    //Find discount by hash
    @Select("SELECT discounts.id, discounts.key, discounts.is_hidden, discounts.user_id, discounts.backer_user_id, discounts.campaign_id, discounts.deleted, discounts.hash, discounts.created_at,\n" +
            "campaigns.name, campaigns.description, campaigns.percent, campaigns.backer_percent,\n" +
            "stores.name, stores.image\n" +
            "FROM discounts INNER JOIN campaigns ON discounts.campaign_id = campaign.id\n" +
            "INNER JOIN stores on campaigns.store_id = stores.id\n" +
            "WHERE discounts.hash = #{hash}")
    @Results({
            @Result(column = "discounts.id", property = "id"),
            @Result(column = "discounts.key", property = "key"),
            @Result(column = "discounts.is_hidden", property = "isHidden"),
            @Result(column = "discounts.user_id", property = "userId"),
            @Result(column = "discounts.backer_user_id", property = "backerUserId"),
            @Result(column = "discounts.campaign_id", property = "campaignId"),
            @Result(column = "discounts.deleted", property = "deleted"),
            @Result(column = "discounts.hash", property = "hash"),
            @Result(column = "discounts.created_at", property = "createdAt"),

            @Result(column = "campaigns.name", property = "campaignName"),
            @Result(column = "campaigns.description", property = "description"),
            @Result(column = "campaigns.percent", property = "percent"),
            @Result(column = "campaigns.backer_percent", property = "backerPercent"),

            @Result(column = "stores.name", property = "storeName"),
            @Result(column = "stores.image", property = "storeImage")
    })
    Discount findDiscountByHash(@Param("hash") String hash);

    //Delete hash by hash
    @Update("UPDATE discounts SET hash = '' WHERE hash = #{hash}")
    void deleteHash(@Param("hash") String hash);

    //Find discount id by email and key
    @Select("SELECT discounts.id FROM discounts\n" +
            "INNER JOIN users on discounts.user_id = users.id\n" +
            "WHERE key = #{key} AND email = #{email}")
    @Results({
            @Result(column = "id")
    })
    Long findDiscountIdByEmailAndKey(@Param("email") String email, @Param("key") String key);

    //Find discount id by maker email and key
    @Select("SELECT discounts.id FROM discounts\n" +
            "INNER JOIN campaigns on discounts.campaign_id = campaigns.id\n" +
            "INNER JOIN stores on campaigns.store_id = stores.id\n" +
            "INNER JOIN users on stores.user_id = users.id\n" +
            "WHERE users.email = #{email} AND discounts.key = #{key}")
    @Results({
            @Result(column = "id")
    })
    Long findDiscountIdByMakerEmailAndKey(@Param("email") String email, @Param("key") String key);

    //Find discount hidden status by key
    @Select("SELECT is_hidden FROM discounts WHERE key = #{key}")
    @Results({
            @Result(column = "is_hidden")
    })
    Boolean findDiscountHiddenStatusByKey(@Param("key") String key);

    //Find id by hash
    @Select("SELECT id FROM discounts WHERE hash = #{hash}")
    @Results({
            @Result(column = "id")
    })
    Long findIdByHash(@Param("hash") String hash);

    //Find discount by key
    @Select("SELECT discounts.id, discounts.key, discounts.is_hidden, discounts.user_id, discounts.backer_user_id, discounts.campaign_id, discounts.deleted, discounts.hash, discounts.created_at,\n" +
            "campaigns.name, campaigns.description, campaigns.percent, campaigns.backer_percent,\n" +
            "stores.name, stores.image\n" +
            "FROM discounts INNER JOIN campaigns ON discounts.campaign_id = campaigns.id\n" +
            "INNER JOIN stores on campaigns.store_id = stores.id\n" +
            "WHERE discounts.key = #{key}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "backer_user_id", property = "backerUserId"),
            @Result(column = "campaign_id", property = "campaignId"),
            @Result(column = "deleted", property = "deleted"),
            @Result(column = "hash", property = "hash"),
            @Result(column = "created_at", property = "createdAt"),

            @Result(column = "name", property = "campaignName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "backer_percent", property = "backerPercent"),

            @Result(column = "name", property = "storeName"),
            @Result(column = "image", property = "storeImage")
    })
    Discount findDiscountByKey(@Param("key") String key);

    //Create feedback discount after use
    @Insert("INSERT INTO discounts (key, user_id, backer_user_id, campaign_id, is_hidden)\n" +
            "VALUES (#{key}, #{userId}, #{backerUserId}, #{campaignId}, #{isHidden})")
    void createFeedbackDiscountAfterUse(Discount discount);
}