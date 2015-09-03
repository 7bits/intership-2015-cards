package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.DiscountHash;
import org.apache.ibatis.annotations.*;

public interface DiscountHashMapper {

    //Save DiscountHash
    @Insert("INSERT INTO discount_hash (discount_id, hash) VALUES (#{discountId}, #{hash})")
    void save(final DiscountHash discountHash);

    //Find Discount Id By Hash
    @Select("SELECT discount_id\n" +
            "FROM discount_hash\n" +
            "WHERE hash=#{hash}")
    @Result(column = "discount_id")
    Long findIdByHash(@Param("hash") String hash);

    //Delete info about temp DiscountHash
    @Delete("DELETE FROM discount_hash\n" +
            "WHERE hash=#{hash}")
    void delete(@Param("hash") String hash);
}


