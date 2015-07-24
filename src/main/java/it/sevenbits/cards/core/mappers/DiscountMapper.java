package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.DiscountForm;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {
    //FindAll
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description FROM discounts")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description")
    })
    List<Discount> findAll();
    //Save
    @Insert("INSERT INTO discounts (key, uin, is_hidden, user_id, store_name, description) VALUES (#{key}, #{uin}, #{isHidden}, #{userId}, #{storeName}, #{description})")
    void save(final Discount discount);
    //Delete
    @Delete("DELETE FROM discounts WHERE uin = #{uin} AND is_hidden = false")
    void delete(final Discount discount);
    //FindAllForUse
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description FROM discounts WHERE is_hidden = false and user_id = #{userName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description")
    })
    List<Discount> findAllForUse(@Param("userName") String userName);
    //FindAllForSend
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description FROM discounts WHERE is_hidden = true and user_id = #{userName}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description")
    })
    List<Discount> findAllForSend(@Param("userName") String userName);
    //FindUserId
    @Select("SELECT id, key, uin, is_hidden, user_id, store_name, description FROM discounts WHERE uin = #{uin}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "store_name", property = "storeName"),
            @Result(column = "description", property = "description")
    })
    List<Discount> findUserId(final Discount discount);
    //ChangeUserId
    @Update("UPDATE discounts SET user_id = #{userId}, is_hidden = false WHERE uin = #{uin} AND user_id != #{userId} AND is_hidden != false") void changeUserId(@Param("uin") String uin, @Param("userId") String userId);
    //Send
    @Update("UPDATE discounts SET user_id = #{userId}, is_hidden = false WHERE uin = #{uin}") void send(@Param("userId") String userId,@Param("uin") String uin,@Param("userName") String userName);
}