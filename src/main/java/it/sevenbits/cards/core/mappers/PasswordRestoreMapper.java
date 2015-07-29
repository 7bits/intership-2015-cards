package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.PasswordRestore;
import org.apache.ibatis.annotations.*;

/**
 * Created by taro on 27.07.15.
 */
public interface PasswordRestoreMapper {
    @Insert("INSERT INTO pw_restore (email, hash) VALUES (#{email}, #{hash})")
    void save(final PasswordRestore passwordRestore);

    @Update("UPDATE users\n" +
            "SET password_hash=#{newPassword}\n" +
            "WHERE email=#{userName}")
    void setNewPassword(@Param("userName") String userName, @Param("newPassword") String newPassword);

    @Select("SELECT email\n" +
            "FROM pw_restore\n" +
            "WHERE hash=#{hash}")
    @Result(column = "email")
    String findEmailByHash(@Param("hash") String hash);

    @Update("UPDATE pw_restore\n" +
            "SET hash=#{newHash}\n" +
            "WHERE email=#{email}")
    void updateHash(@Param("newHash") String newHash, @Param("email") String email);

    @Select("SELECT hash\n" +
            "FROM pw_restore\n" +
            "WHERE email=#{email}")
    @Result(column = "hash")
    String findHashByEmail(@Param("email") String email);

    @Delete("DELETE FROM pw_restore\n" +
            "WHERE email=#{email}")
    void delete(@Param("email") String email);
}
