package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {
    //FindAll
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image FROM discounts")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage")
    })
    List<Discount> findAll();
    //Save
    @Insert("INSERT INTO discounts (key, uin, is_hidden, user_id, store_name, description, percent, store_image) VALUES (#{key}, #{uin}, #{isHidden}, #{userId}, #{storeName}, #{description}, #{percent})")
    void save(final Discount discount);
    //Save
    @Insert("INSERT INTO discounts (key, uin, is_hidden, user_id, store_name, description, percent, store_image) VALUES (#{key}, #{uin}, #{isHidden}, #{userId}, #{storeName}, #{description}, #{percent}, #{storeImage})")
    void saveByAcoustics(final Discount discount);
    //Delete
    @Delete("DELETE FROM discounts WHERE key = #{key} AND store_name = #{storeName}")
    void delete(@Param("key") String key, @Param("storeName") String storeName);
    //FindAllForUse
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image FROM discounts WHERE is_hidden = false and user_id = #{userName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "store_image", property = "storeImage")
    })
    List<Discount> findAllForUse(@Param("userName") String userName);
    //FindAllForSend
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, store_image FROM discounts WHERE is_hidden = true and user_id = #{userName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage")
    })
    List<Discount> findAllForSend(@Param("userName") String userName);
    //FindUserId
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, store_image FROM discounts WHERE uin = #{uin}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage")
    })
    List<Discount> findUserId(final Discount discount);
    //ChangeUserId
    @Update("UPDATE discounts SET user_id = #{userId}, is_hidden = false WHERE uin = #{uin}") void changeUserId(@Param("uin") String uin, @Param("userId") String userId);
    //Send
    @Update("UPDATE discounts SET user_id = #{userId}, is_hidden = false WHERE uin = #{uin}") void send(@Param("userId") String userId, @Param("uin") String uin);
    //Find Discount Owner
    @Select("SELECT user_id\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Result(column = "user_id")
    String findDiscountOwner(@Param("uin") String uin);
    //Find Discount maker
    @Select("SELECT store_name\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Result(column = "store_name")
    String findDiscountMaker(@Param("key") String key);
    //Find Hidden Status By Key
    @Select("SELECT is_hidden\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Result(column = "is_hidden")
    Boolean findHiddenStatusByKey(@Param("key") String key);
    //Find Hidden Status By Uin
    @Select("SELECT is_hidden\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Result(column = "is_hidden")
    Boolean findHiddenStatusByUin(@Param("uin") String uin);
    //Find Discount Id By Key
    @Select("SELECT id\n" +
            "FROM discounts\n" +
            "WHERE key=#{key}")
    @Result(column = "id")
    Long findDiscountIdByKey(@Param("key") String key);
    //Find Discount Id By Uin
    @Select("SELECT id\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Result(column = "id")
    Long findDiscountIdByUin(@Param("uin") String uin);

    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image\n" +
            "FROM discounts\n" +
            "WHERE uin=#{uin}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage")
    })
    Discount findDiscountByUin(@Param("uin") String uin);

    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description, percent, store_image\n" +
            "FROM discounts\n" +
            "WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description"),
            @Result(column = "percent", property = "percent"),
            @Result(column = "store_image", property = "storeImage")
    })
    Discount findDiscountById(@Param("id") Long id);
}