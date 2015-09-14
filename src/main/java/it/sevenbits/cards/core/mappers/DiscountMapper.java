package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {

    //Save
    @Insert("INSERT INTO discounts (key, user_id, backer_user_id, campaign_id)\n" +
            "VALUES (#{key}, (select id from users where email = #{email}), (select id from users where email = #{email}), #{campaignId})")
    void createByCampaign(@Param("key") String key, @Param("email") String email, @Param("campaignId") Long campaignId);

    //Delete discount by key
    @Delete("DELETE FROM discounts where discounts.key = #{key} AND discount.is_hidden = false AND\n +" +
            "(SELECT users.email FROM users INNER JOIN stores ON users.id = stores.user_id\n" +
            "INNER JOIN campaigns on campaigns.store_id = stores.id\n" +
            "WHERE discounts.campaign_id = campaigns.id) = #{email}")
    void delete(@Param("key") String key, @Param("email") String email);

    //Find all discounts with hidden or not hidden status
    @Select("SELECT discounts.id, discounts.key, discounts.is_hidden, discounts.user_id, discounts.backer_user_id, discounts.campaign_id, discounts.deleted, discounts.hash, discounts.created_at,\n" +
            "campaigns.name, campaigns.description, campaigns.percent, campaigns.backer_percent,\n" +
            "stores.name, stores.image\n" +
            "FROM discounts INNER JOIN campaigns ON discounts.campaign_id = campaign.id\n" +
            "INNER JOIN stores on campaigns.store_id = stores.id\n" +
            "INNER JOIN users on discounts.user_id = users.id\n" +
            "WHERE users.email = #{email} AND discounts.is_hidden = #{isHidden}")
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

    //Add hash for exist discount
    @Update("UPDATE discounts SET hash = #{hash} WHERE id = #{id}")
    void addHash(@Param("id") Long id, @Param("hash") String hash);

    //Delete hash for exist discount
    @Update("UPDATE discounts SET hash = null WHERE hash=#{hash}")
    void deleteHash(@Param("hash") String hash);
}