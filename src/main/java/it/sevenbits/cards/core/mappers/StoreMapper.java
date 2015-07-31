package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Store;
import org.apache.ibatis.annotations.*;

public interface StoreMapper {
    @Insert("INSERT INTO stores (user_id, store_name, store_image, description, discount_percent) VALUES (#{userId}, #{storeName}, #{storeImage}, #{description}, #{discountPercent})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(final Store store);

    @Insert("INSERT INTO stores (user_id, store_name, store_image, description, discount_percent) VALUES (#{userId}, #{storeName}, #{storeImage}, #{description}, #{discountPercent})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void saveChanges(final Store store);

    /*
        "description" VARCHAR(4000),
        "discount_percent" INTEGER NOT NULL
    */

    @Select("SELECT id, user_id, store_name, store_image, description, discount_percent\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "store_image", property = "storeImage"),
            @Result(column = "description", property = "description"),
            @Result(column = "discount_percent", property = "discountPercent")
    })
    Store findStoreByUserId(@Param("userId") String userId);

    @Select("SELECT store_name\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Result(column = "store_name")
    String findStoreNameByUserId(@Param("userId") String userId);

    @Update("UPDATE stores\n" +
            "SET discount_percent=#{discountPercent}\n" +
            "WHERE user_id=#{userId}")
    void updateDiscountPercent(@Param("userId") String userId, @Param("discountPercent") int discountPercent);

    @Update("UPDATE stores\n" +
            "SET description=#{description}\n" +
            "WHERE user_id=#{userId}")
    void updateDescription(@Param("userId") String userId, @Param("description") String description);
}
