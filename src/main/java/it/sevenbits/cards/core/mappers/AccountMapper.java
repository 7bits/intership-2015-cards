package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Account;
import org.apache.ibatis.annotations.*;

public interface AccountMapper {

    @Insert("INSERT INTO accounts (email, hash) VALUES (#{email}, #{hash})")
    void save(final Account account);

    @Delete("DELETE FROM accounts\n" +
            "WHERE email=#{email}")
    void delete(@Param("email") String email);

    @Select("SELECT email\n" +
            "FROM accounts\n" +
            "WHERE hash=#{hash}")
    @Result(column = "email")
    String findEmailByHash(@Param("hash") String hash);

    //Find hash by email
    @Select("SELECT hash\n" +
            "FROM accounts\n" +
            "WHERE email=#{email}")
    @Result(column = "hash")
    String findHashByEmail(@Param("email") String email);

    //Update hash by email
    @Update("UPDATE accounts\n" +
            "SET hash=#{hash}\n" +
            "WHERE email=#{email}")
    void updateHash(@Param("hash") String hash, @Param("email") String email);

}
