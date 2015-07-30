package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Store;
import org.apache.ibatis.annotations.*;

public interface StoreMapper {
    @Insert("INSERT INTO stores (user_id, store_name, store_image, describe, discount) VALUES (#{userId}, #{storeName}, #{storeImage}, #{describe}, #{discount})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(final Store store);

    @Insert("INSERT INTO stores (user_id, store_name, store_image, describe, discount) VALUES (#{userId}, #{storeName}, #{storeImage}, #{describe}, #{discount})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void saveChanges(final Store store);


    @Select("SELECT id, user_id, store_name, store_image, describe, discount\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "describe", property = "describe"),
            @Result(column = "discount", property = "discount")
    })
    Store findStoreByUserId(@Param("userId") String userId);

    @Select("SELECT store_name\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Result(column = "store_name")
    String findStoreNameByUserId(@Param("userId") String userId);

    @Update("UPDATE stores\n" +
            "SET discount=#{discount}\n" +
            "WHERE user_id=#{userId}")
    void updateDiscount(@Param("userId") String userId, @Param("discount") int discount);

    @Update("UPDATE stores\n" +
            "SET describe=#{describe}\n" +
            "WHERE user_id=#{userId}")
    void updateDescribe(@Param("userId") String userId, @Param("describe") String describe);
}
