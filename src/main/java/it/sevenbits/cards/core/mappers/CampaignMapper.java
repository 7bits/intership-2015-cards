package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CampaignMapper {
    //Save
    @Insert("INSERT INTO campaigns (store_id, name, description, percent, backer_percent)\n" +
            "VALUES (" +
            "(select stores.id from stores\n" +
            "inner join users on stores.user_id = users.id\n" +
            "where users.email = #{email}\n" +
            "), #{campaign.name}, #{campaign.description}, #{campaign.percent}, #{campaign.backerPercent})")
    void save(@Param("campaign") Campaign campaign, @Param("email") String email);

    //Find All
    @Select("SELECT campaigns.id, campaigns.store_id, campaigns.name, campaigns.description, campaigns.percent, campaigns.backer_percent, campaigns.enabled, campaigns.created_at\n" +
            "FROM campaigns INNER JOIN stores on campaigns.store_id = stores.id\n" +
            "INNER JOIN users ON stores.user_id = users.id WHERE users.email = #{email} AND campaigns.enabled = #{enabled}")
    @Results({
            @Result(column = "campaigns.id", property = "id"),
            @Result(column = "campaigns.store_id", property = "storeId"),
            @Result(column = "campaigns.name", property = "name"),
            @Result(column = "campaigns.description", property = "description"),
            @Result(column = "campaigns.percent", property = "percent"),
            @Result(column = "campaigns.backer_percent", property = "backerPercent"),
            @Result(column = "campaigns.enabled", property = "enabled"),
            @Result(column = "campaigns.created_at", property = "createdAt")
    })
    List<Campaign> findAllWithEnabledStatus(@Param("email") String email, @Param("enabled") Boolean enabled);

    //Change campaign enable status
    @Update("UPDATE campaigns SET enabled = not enabled where id = #{id}")
    void changeEnableStatus(@Param("id") Long id);
}