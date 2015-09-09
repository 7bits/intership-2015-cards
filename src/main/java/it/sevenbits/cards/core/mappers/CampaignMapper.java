package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CampaignMapper {
    /*
    private Long id;
    private Long storeId;
    private String name;
    private String description;
    private Long percent;
    private Long backerPercent;
    private Boolean enabled;
    private Timestamp createdAt;
    */

    //Save
    @Insert("INSERT INTO campaigns (store_id, name, description, percent, backer_percent)\n" +
            "VALUES (#{store_id}, #{name}, #{description}, #{percent}, #{backer_percent}")
    @Options(useGeneratedKeys = true)
    void save(final Campaign campaign);

    //Find All
    @Select("SELECT id, store_id, name, description, percent, backer_percent, enabled, created_at\n" +
            "FROM campaigns INNER JOIN stores on campaigns.store_id = stores.id\n +" +
            "WHERE enabled = #{enabled} AND stores.user_id = (select id from users where email=#{email}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_id", property = "storeId"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "backer_percent", property = "backerPercent"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "created_at", property = "createdAt")
    })
    List<Campaign> findAllWithEnabledStatus(@Param("email") String email, @Param("enabled") Boolean enabled);

    //Change campaign enable status
    @Update("UPDATE campaigns SET enabled = not enabled where id = #{id}")
    void changeEnableStatus(@Param("id") Long id);
}