package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.web.domain.DiscountForm;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {
    @Select("SELECT id, key, uin, is_hidden, user_id FROM discounts")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "is_hidden", property = "isHidden"),
            @Result(column = "user_id", property = "userId")
    })
    List<Discount> findAll();
    @Insert("INSERT INTO discounts (key, uin, is_hidden, user_id) VALUES (#{key}, #{uin}, #{isHidden}, #{userId})")
    void save(final Discount discount);
    @Delete("DELETE FROM discounts WHERE uin = #{uin}")
    void delete(final Discount discount);
}
