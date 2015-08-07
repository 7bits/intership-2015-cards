package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CampaignMapper {
    //Save
    @Insert("INSERT INTO campaign (store_name, name, description, percent, enabled) VALUES (#{storeName}, #{name}, #{description}, #{percent}, #{enabled})")
    void save(final Campaign campaign);
    //Find All Active
    @Select("SELECT id, store_name, name, description, percent, enabled FROM campaign WHERE enabled = true and store_name = #{storeName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "enabled", property = "enabled")
    })
    List<Campaign> findAllActive(@Param("storeName") String storeName);
    //Find All Not Active
    @Select("SELECT id, store_name, name, description, percent, enabled FROM campaign WHERE enabled = false and store_name = #{storeName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "name", property = "name"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "enabled", property = "enabled")
    })
    List<Campaign> findAllNotActive(@Param("storeName") String storeName);
}