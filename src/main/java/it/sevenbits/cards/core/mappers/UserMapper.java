package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;


public interface UserMapper {

    /*
       private Long id;
    private String email;
    private String password;
    private Role role;
    private Boolean enabled;
    private String accountHash;
    private Timestamp createdAt;
    private Timestamp updatedAt;
     */

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
    @Insert("INSERT INTO users (email, password_hash, role, account_hash)\n" +
            "VALUES (#{email}, #{password}, #{role}, #{accountHash})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(User user);

    //Change user role by email
    @Update("UPDATE users SET role =#{userRole} WHERE email = #{email}")
    void changeUserRoleByEmail(@Param("userRole") String userRole, @Param("email") String email);

    //Change enable status by email
    @Update("UPDATE users SET enabled = not enabled WHERE email = #{email}")
    void changeEnableStatusByEmail(@Param("email") String email);

    //Set new password
    @Update("UPDATE users\n" +
            "SET password_hash=#{passwordHash}\n" +
            "WHERE email=#{email}")
    void setNewPassword(@Param("email") String email, @Param("passwordHash") String passwordHash);
}
