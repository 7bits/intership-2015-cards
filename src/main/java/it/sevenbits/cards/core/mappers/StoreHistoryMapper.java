package it.sevenbits.cards.core.mappers;


import it.sevenbits.cards.core.domain.StoreHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreHistoryMapper {
    //FindAll
    @Select("SELECT id, store_name, description , created_at FROM store_history WHERE store_name = #{storeName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "created_at", property = "createdAt")

    })
    List<StoreHistory> findAll(@Param("storeName") String storeName);
    //Save
    @Insert("INSERT INTO store_history (store_name, description) VALUES (#{storeName}, #{description})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(StoreHistory storeHistory);
}
