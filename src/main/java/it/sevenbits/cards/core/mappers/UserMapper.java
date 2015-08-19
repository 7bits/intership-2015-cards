package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;


public interface UserMapper {

    @Select("SELECT id, email, user_id, password_hash, role, enabled\n" +
            "FROM users\n" +
            "WHERE email=#{userName}")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "email", property = "email"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "password_hash", property = "password"),
        @Result(column = "role", property = "role", javaType = Role.class),
        @Result(column = "enabled", property = "enabled")
    })
    User findByUsername(@Param("userName") String userName);

    @Select("SELECT id, email, user_id, password_hash, role, enabled\n" +
            "FROM users\n" +
            "WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "email", property = "email"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "password_hash", property = "password"),
            @Result(column = "role", property = "role", javaType = Role.class),
            @Result(column = "enabled", property = "enabled")
    })
    User findById(@Param("id") Long id);

    @Insert("INSERT INTO users (email, user_id, password_hash, role)\n" +
            "VALUES (#{email}, #{userId}, #{password}, 'ROLE_USER')")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(User user);

    @Select("SELECT max(user_id)\n" +
            "FROM users")
    @Result(column = "user_id")
    String maxUserId();

    @Select("SELECT user_id\n" +
            "FROM users\n" +
            "WHERE email=#{userName}")
    @Result(column = "user_id")
    String findUserIdByUserName(@Param("userName") String userName);

    @Update("UPDATE users SET role =#{userRole} WHERE user_id = #{userId}")
    void changeUserRoleByUserId(@Param("userRole") String userRole, @Param("userId") String userId);

    @Select("SELECT created_at\n" +
            "FROM users\n" +
            "WHERE email=#{email}")
    @Result(column = "created_at")
    Timestamp findCreateAtTimeByEmail(@Param("email") String email);
}
