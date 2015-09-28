package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Role;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.mappers.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class UserRepository implements UserDetailsService {
    private static final Logger LOG = Logger.getLogger(UserRepository.class);

    @Autowired
    private UserMapper userMapper;


    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            LOG.info("Loading user by email: " + email);
            User userDetails = this.findByEmail(email);
            if (userDetails != null && (userDetails.getRole().equals(Role.ROLE_ADMIN)||userDetails.getRole().equals(Role.ROLE_STORE)||userDetails.getRole().equals(Role.ROLE_USER))) {
                return userDetails;
            }
        } catch (Exception e) {
            LOG.error("Cant load user by email due to repository error: " + e.getMessage(), e);
            throw new UsernameNotFoundException("User details can not be obtained because of " + e.getMessage(), e);
        }
        LOG.info("Cannot load user by email because there are no user details for this email.");
        throw new UsernameNotFoundException("There are no user details for this email");
    }

    public void save(final User user) throws RepositoryException {
        if (user == null) {
            throw new RepositoryException("Null user received");
        }
        try {
            userMapper.save(user);
        } catch (Exception e) {
            throw new RepositoryException("General database error" + e.getMessage(), e);
        }
    }
    public User findByEmail(final String email) throws RepositoryException {
        if (email == null) {
            throw new RepositoryException("User Name is null");
        }
        try {
            return userMapper.findByEmail(email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while finding user by email: " + e.getMessage(), e);
        }
    }

    public void changeUserRoleByEmail(String userRole, String email) throws RepositoryException {
        if (userRole == null) {
            throw new RepositoryException("User Role is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            userMapper.changeUserRoleByEmail(userRole, email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while changing user role by email: " + e.getMessage(), e);
        }
    }

    public void activateByHash(String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            userMapper.activateByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while activating user by hash: " + e.getMessage(), e);
        }
    }

    public void restorePassword(String passwordHash, String hash) throws RepositoryException{
        if (passwordHash == null) {
            throw new RepositoryException("Password hash is null");
        }
        if (hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            userMapper.restorePassword(passwordHash, hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while restoring password by hash: " + e.getMessage(), e);
        }
    }
    public Long findIdByHash(String hash) throws RepositoryException{
        if(hash == null) {
            throw new RepositoryException("Hash is null");
        }
        try{
            return userMapper.findIdByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while finding id by hash: " + e.getMessage(), e);
        }
    }
    public void update(final User user) throws RepositoryException {
        if (user == null) {
            throw new RepositoryException("Null user received");
        }
        try {
            userMapper.update(user);
        } catch (Exception e) {
            throw new RepositoryException("General database error" + e.getMessage(), e);
        }
    }
}
