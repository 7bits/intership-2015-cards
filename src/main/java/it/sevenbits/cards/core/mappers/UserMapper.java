package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import org.apache.ibatis.annotations.*;



public interface UserMapper {

    @Select("SELECT id, email, user_id, password_hash, role, enabled, is_store\n" +
            "FROM users\n" +
            "WHERE email=#{userName}")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "email", property = "email"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "password_hash", property = "password"),
        @Result(column = "role", property = "role", javaType = Role.class),
        @Result(column = "enabled", property = "enabled"),
        @Result(column = "is_store", property = "isStore")
    })
    User findByUsername(@Param("userName") String userName);

    @Select("SELECT id, email, user_id, password_hash, role, enabled, is_store\n" +
            "FROM users\n" +
            "WHERE id=#{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "email", property = "email"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "password_hash", property = "password"),
            @Result(column = "role", property = "role", javaType = Role.class),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "is_store", property = "isStore")
    })
    User findById(@Param("id") Long id);

    @Insert("INSERT INTO users (email, user_id, password_hash, role)\n" +
            "VALUES (#{email}, #{userId}, #{password}, 'ROLE_USER')")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(User user);
}
