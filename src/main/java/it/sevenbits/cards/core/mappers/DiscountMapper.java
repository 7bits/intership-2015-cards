package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.DiscountForm;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {
    //FindAll
    @Select("SELECT id, key, uin, is_hidden, user_id FROM discounts")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId")
    })
    List<Discount> findAll();
    //save
    @Insert("INSERT INTO discounts (key, uin, is_hidden, user_id) VALUES (#{key}, #{uin}, #{isHidden}, #{userId})")
    void save(final Discount discount);
    //delete
    @Delete("DELETE FROM discounts WHERE uin = #{uin}")
    void delete(final Discount discount);
    //findAllDiscountsToUse
    @Select("SELECT id, key, uin, is_hidden, user_id FROM discounts WHERE is_hidden = false")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId")
    })
    List<Discount> findAllDiscountsToUse();
    //findAllDiscountsToSend
    @Select("SELECT id, key, uin, is_hidden, user_id FROM discounts WHERE is_hidden = true")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId")
    })
    List<Discount> findAllDiscountsToSend();
    //findRegisteredUsers
    @Select("SELECT id, email, user_id, password, is_store FROM users WHERE email = #{email} AND  password = #{password}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "email", property = "email"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "password", property = "password"),
            @Result(column = "is_store", property = "isStore")
    })
    boolean findRegisteredUsers(final User user);
}
