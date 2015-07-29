package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Store;
import org.apache.ibatis.annotations.*;

public interface StoreMapper {
    @Insert("INSERT INTO stores (user_id, store_name, store_image) VALUES (#{userId}, #{storeName}, #{storeImage})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(final Store store);

    @Select("SELECT store_name\n" +
            "FROM stores\n" +
            "WHERE user_id=#{userId}")
    @Result(column = "store_name")
    String findStoreNameByUserId(@Param("userId") String userId);
}
