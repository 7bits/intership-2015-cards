package it.sevenbits.cards.core.mappers;


import it.sevenbits.cards.core.domain.StoreHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StoreHistoryMapper {
    //FindAll
    @Select("SELECT id, store_name, description FROM store_history WHERE store_name = #{storeName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description")
    })
    List<StoreHistory> findAll(@Param("storeName") String storeName);
}
