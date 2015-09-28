package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;


public interface UserMapper {
    //Find User by email
    @Select("SELECT id, email, password_hash, role, enabled, account_hash, created_at, updated_at\n" +
            "FROM users\n" +
            "WHERE email=#{email}")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "email", property = "email"),
        @Result(column = "password_hash", property = "password"),
        @Result(column = "role", property = "role", javaType = Role.class),
        @Result(column = "enabled", property = "enabled"),
        @Result(column = "account_hash", property = "accountHash"),
        @Result(column = "created_at", property = "createdAt"),
        @Result(column = "updated_at", property = "updatedAt")
    })
    User findByEmail(@Param("email") String email);

    //Save User
    @Insert("INSERT INTO users(email, role, account_hash) VALUES (#{email}, #{role}, #{accountHash})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(User user);

    //Update User
    @Update("UPDATE users SET password_hash = #{password} WHERE email = #{email}")
    void update(User user);

    //Change user role by email
    @Update("UPDATE users SET role =#{userRole} WHERE email = #{email}")
    void changeUserRoleByEmail(@Param("userRole") String userRole, @Param("email") String email);

    @Update("UPDATE users SET account_hash = '', enabled = true WHERE account_hash = #{hash}")
    void activateByHash(@Param("hash") String hash);

    @Update("UPDATE users SET password_hash = #{passwordHash} WHERE account_hash = #{hash}")
    void restorePassword(@Param("passwordHash") String passwordHash, @Param("hash") String hash);

    //Find id by hash
    @Select("SELECT id FROM users where account_hash = #{hash}")
    @Results({
            @Result(column = "id")
    })
    Long findIdByHash(@Param("hash") String hash);
}
