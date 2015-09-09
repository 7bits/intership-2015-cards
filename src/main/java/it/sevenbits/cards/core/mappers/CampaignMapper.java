package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CampaignMapper {
    //Save
    @Insert("INSERT INTO campaigns (store_id, name, description, percent, backer_percent) VALUES (#{store_id}, #{name}, #{description}, #{percent}, #{backer_percent}")
    @Options(useGeneratedKeys = true)
    void save(final Campaign campaign);

    //Find All Active
    @Select("SELECT id, store_id, name, description, percent, backer_percent, enabled, created_at FROM campaigns WHERE enabled = #{enabled} and store_id = #{storeId}")
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
    List<Campaign> findAllWithEnabledStatus(@Param("storeId") Long storeId, @Param("enabled") Boolean enabled);

    //Change campaign enable status
    @Update("UPDATE campaigns SET enabled = not enabled where id = #{id}")
    void changeEnableStatus(@Param("id") Long id);
}