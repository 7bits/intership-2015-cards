package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.PasswordRestore;
import org.apache.ibatis.annotations.*;

public interface PasswordRestoreMapper {

    //Save
    @Insert("INSERT INTO pw_restore (email, hash) VALUES (#{email}, #{hash})")
    void save(final PasswordRestore passwordRestore);

    //Set new password
    @Update("UPDATE users\n" +
            "SET password_hash=#{newPassword}\n" +
            "WHERE email=#{userName}")
    void setNewPassword(@Param("userName") String userName, @Param("newPassword") String newPassword);

    //Find email by hash
    @Select("SELECT email\n" +
            "FROM pw_restore\n" +
            "WHERE hash=#{hash}")
    @Result(column = "email")
    String findEmailByHash(@Param("hash") String hash);

    //Update hash by email
    @Update("UPDATE pw_restore\n" +
            "SET hash=#{newHash}\n" +
            "WHERE email=#{email}")
    void updateHash(@Param("newHash") String newHash, @Param("email") String email);

    //Find hash by email
    @Select("SELECT hash\n" +
            "FROM pw_restore\n" +
            "WHERE email=#{email}")
    @Result(column = "hash")
    String findHashByEmail(@Param("email") String email);

    //Delete hash by email
    @Delete("DELETE FROM pw_restore\n" +
            "WHERE email=#{email}")
    void delete(@Param("email") String email);
}
