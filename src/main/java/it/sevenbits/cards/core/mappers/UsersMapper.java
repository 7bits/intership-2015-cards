package it.sevenbits.cards.core.mappers;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import org.apache.ibatis.annotations.Insert;

public interface UsersMapper {
    @Insert("INSERT INTO users (email, user_id, password_hash, is_store) VALUES (#{email}, #{userId}, #{passwordHash}, #{isStore})")
    void saveUser(final User user);
}
