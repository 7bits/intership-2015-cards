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

@Repository
public class UserRepository implements UserDetailsService {
    private static final Logger LOG = Logger.getLogger(UserRepository.class);

    @Autowired
    private UserMapper userMapper;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            LOG.info("Loading user by username: " + username);
            User userDetails = this.findByUsername(username);
            if (userDetails != null && (userDetails.getRole().equals(Role.ROLE_ADMIN)||userDetails.getRole().equals(Role.ROLE_STORE)||userDetails.getRole().equals(Role.ROLE_USER))) {
                return userDetails;
            }
        } catch (Exception e) {
            LOG.error("Cant load user by username due to repository error: " + e.getMessage(), e);
            throw new UsernameNotFoundException("User details can not be obtained because of " + e.getMessage(), e);
        }
        LOG.info("Cannot load user by username because there are no user details for this username.");
        throw new UsernameNotFoundException("There are no user details for this username");
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

    public User findById(final Long id) throws RepositoryException {
        if (id == null) {
            throw new RepositoryException("User id is null");
        }
        return userMapper.findById(id);
    }

    public User findByUsername(final String username) throws RepositoryException {
        if (username == null) {
            throw new RepositoryException("User Name is null");
        }
        try {
            return userMapper.findByUsername(username);
        } catch (Exception e) {
            throw new RepositoryException("General database error " + e.getMessage(), e);
        }
    }

    public String maxUserId() throws RepositoryException {
        try {
            return userMapper.maxUserId();
        } catch (Exception e) {
            throw new RepositoryException("General database error " + e.getMessage(), e);
        }
    }

    public String findUserIdByUserName(final String userName) throws RepositoryException {
        if (userName == null) {
            throw new RepositoryException("User Name is null");
        }
        try {
            return userMapper.findUserIdByUserName(userName);
        } catch (Exception e) {
            throw new RepositoryException("General database error " + e.getMessage(), e);
        }
    }

    public void changeUserRoleByUserId(String userRole, String userId) throws RepositoryException {
        if (userRole == null) {
            throw new RepositoryException("User Role is null");
        }
        if (userId == null) {
            throw new RepositoryException("User Id is null");
        }
        try {
            userMapper.changeUserRoleByUserId(userRole, userId);
        } catch (Exception e) {
            throw new RepositoryException("General database error " + e.getMessage(), e);
        }
    }

}