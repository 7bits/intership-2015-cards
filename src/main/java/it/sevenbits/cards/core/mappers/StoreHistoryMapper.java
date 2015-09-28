package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.StoreHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StoreHistoryMapper {

    //FindAll
    @Select("SELECT stores_history.id, stores_history.store_id, stores_history.description, stores_history.created_at FROM stores_history\n" +
            "INNER JOIN stores on stores_history.store_id = stores.id\n" +
            "INNER JOIN users ON stores.user_id = users.id WHERE users.email = #{email}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "store_id", property = "storeId"),
            @Result(column = "description", property = "description"),
            @Result(column = "created_at", property = "createdAt")
    })
    List<StoreHistory> findAll(@Param("email") String email);

    //Save
    @Insert("INSERT INTO stores_history (store_id, description)\n"+
            "VALUES ((SELECT stores.id FROM stores\n" +
            "INNER JOIN users ON stores.user_id = users.id\n" +
            "WHERE users.email=#{email}), #{storeHistory.description})")
    void save(@Param("storeHistory") StoreHistory storeHistory, @Param("email") String email);
}
