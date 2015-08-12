package it.sevenbits.cards.core.mappers;

/**
 * Created by deamor on 12.08.15.
 */
import it.sevenbits.cards.core.domain.AccountActivation;
import org.apache.ibatis.annotations.*;
public interface AccountActivationMapper {
    @Insert("INSERT INTO account_activation (email, hash) VALUES (#{email}, #{hash})")
    void save(final AccountActivation accountActivation);

    @Select("SELECT email\n" +
            "FROM account_activation\n" +
            "WHERE hash=#{hash}")
    @Result(column = "email")
    String findEmailByHash(@Param("hash") String hash);

    @Update("UPDATE account_activation\n" +
            "SET hash=#{newHash}\n" +
            "WHERE email=#{email}")
    void updateHash(@Param("newHash") String newHash, @Param("email") String email);

    @Select("SELECT hash\n" +
            "FROM account_activation\n" +
            "WHERE email=#{email}")
    @Result(column = "hash")
    String findHashByEmail(@Param("email") String email);

    @Delete("DELETE FROM account_activation\n" +
            "WHERE email=#{email}")
    void delete(@Param("email") String email);
}
