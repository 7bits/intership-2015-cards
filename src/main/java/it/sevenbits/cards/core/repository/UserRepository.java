package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by taro on 22.07.15.
 */
public interface UserRepository {
//    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
    public void save(final User user) throws RepositoryException;
    public User findById(final Long id) throws RepositoryException;
    public User findByUsername(final String username) throws RepositoryException;
}
