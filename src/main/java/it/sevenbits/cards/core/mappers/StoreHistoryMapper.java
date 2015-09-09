package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.StoreHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreHistoryMapper {

    //FindAll
    @Select("SELECT id, store_id, description, created_at FROM stores_history WHERE store_id = #{storeId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_id", property = "storeId"),
            @Result(column = "description", property = "description"),
            @Result(column = "created_at", property = "createdAt")

    })
    List<StoreHistory> findAll(@Param("storeId") Long storeId);

    //Save
    @Insert("INSERT INTO stores_history (store_id, description) VALUES (#{storeId}, #{description})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(StoreHistory storeHistory);
}
