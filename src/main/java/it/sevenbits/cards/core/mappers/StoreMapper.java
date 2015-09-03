package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Store;
import org.apache.ibatis.annotations.*;

public interface StoreMapper {

    //Save Store
    @Insert("INSERT INTO stores (user_id, store_name, store_image) VALUES (#{userId}, #{storeName}, #{storeImage})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(final Store store);

    //Find store by user id
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

    //Find storeName by userId
    @Select("SELECT store_name\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Result(column = "store_name")
    String findStoreNameByUserId(@Param("userId") String userId);

    //Find StoreImage by StoreName
    @Select("SELECT store_image\n" +
            "FROM stores\n" +
            "WHERE store_name=#{storeName}")
    @Result(column = "store_image")
    String findStoreImageByStoreName(@Param("storeName") String storeName);


}
