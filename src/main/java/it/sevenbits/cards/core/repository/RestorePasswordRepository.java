package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.PasswordRestore;
import it.sevenbits.cards.core.mappers.PasswordRestoreMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

/**
 * Created by taro on 27.07.15.
 */
@Repository
public class RestorePasswordRepository{
        private static final Logger LOG = Logger.getLogger(RestorePasswordRepository.class);

    @Autowired
    private PasswordRestoreMapper restoreMapper;

    public void save(final PasswordRestore restore) throws RepositoryException {
        if (restore == null) {
            throw new RepositoryException("Null user received");
        }
        try {
            restoreMapper.save(restore);
        } catch (Exception e) {
            throw new RepositoryException("General database error" + e.getMessage(), e);
        }
    }

    public void setNewPassword(final String userName, final String newPassword) throws RepositoryException {
        if (userName == null) {
            throw new RepositoryException("email is empty");
        }
        if (newPassword == null) {
            throw new RepositoryException("password is empty");
        }
        try {
            restoreMapper.setNewPassword(userName, newPassword);
        } catch (Exception e) {
            throw new RepositoryException("Set new password database error" + e.getMessage(), e);
        }
    }

    public String findEmailByHash(String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("hash is empty");
        }
        try {
            return restoreMapper.findEmailByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("Find email by hash database error" + e.getMessage(), e);
        }
    }

    public void delete(final String email) throws RepositoryException{
        if (email == null) {
            throw new RepositoryException("email is empty in delete()");
        }
        try {
            restoreMapper.delete(email);
        } catch (Exception e) {
            throw new RepositoryException("delete() database error" + e.getMessage(), e);
        }
    }

    public void updateHash(String newHash, String email) throws RepositoryException {
        try {
            restoreMapper.updateHash(newHash, email);
        } catch (Exception e) {
            throw new RepositoryException("updateHash() repository error");
        }
    }

    public String findHashByEmail(final String email) throws RepositoryException {
        try {
            return restoreMapper.findHashByEmail(email);
        } catch (Exception e) {
            throw new RepositoryException("findHashByEmail() reposiroty error");
        }
    }
}
