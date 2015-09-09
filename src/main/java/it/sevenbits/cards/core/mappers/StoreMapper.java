package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Store;
import org.apache.ibatis.annotations.*;

public interface StoreMapper {
    
    //Save Store
    @Insert("INSERT INTO stores (user_id, store_name, store_image)\n" +
            "VALUES (#{userId}, #{storeName}, #{storeImage})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(final Store store);

    //Find store by user id
    @Select("SELECT id, user_id, store_name, store_image, created_at FROM stores\n" +
            "INNER JOIN users ON stores.user_id = users.id\n" +
            "WHERE stores.user_id = (select id from users where email = #{email})")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "created_at", property = "createdAt")
    })
    Store findByEmail(@Param("email") String email);

}
