package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Store;
import org.apache.ibatis.annotations.*;

public interface StoreMapper {
    @Insert("INSERT INTO stores (user_id, store_name, store_image) VALUES (#{userId}, #{storeName}, #{storeImage})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(final Store store);

    @Insert("INSERT INTO stores (user_id, store_name, store_image) VALUES (#{userId}, #{storeName}, #{storeImage})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void saveChanges(final Store store);

    /*
        "description" VARCHAR(4000),
        "discount_percent" INTEGER NOT NULL
    */

    @Select("SELECT id, user_id, store_name, store_image\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "store_image", property = "storeImage")
    })
    Store findStoreByUserId(@Param("userId") String userId);

    @Select("SELECT store_name\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Result(column = "store_name")
    String findStoreNameByUserId(@Param("userId") String userId);


    @Select("SELECT store_image\n" +
            "FROM stores\n" +
            "WHERE store_name=#{storeName}")
    @Result(column = "store_image")
    String findStoreImageByStoreName(@Param("storeName") String storeName);


}
