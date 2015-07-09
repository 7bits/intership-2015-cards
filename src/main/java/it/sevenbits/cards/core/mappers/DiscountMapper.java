package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface DiscountMapper {
    @Select("SELECT `id`, `key`, `uin`, `isHidden`, `userId` FROM `discounts`")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "key", property = "key"),
            @Result(column = "uin", property = "uin"),
            @Result(column = "isHidden", property = "isHidden"),
            @Result(column = "userId", property = "userId")
    })
    List<Discount> findAll();
    @Insert("INSERT INTO `discounts` (`key`, `uin`, `isHidden`, `userId`) VALUES (#{key}, #{uin}, #{isHidden}, #{userId})")
    void save(final Discount discount);
}
