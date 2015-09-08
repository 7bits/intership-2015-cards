package it.sevenbits.cards.core.repository;


import it.sevenbits.cards.core.domain.AccountActivation;
import it.sevenbits.cards.core.domain.PasswordRestore;
import it.sevenbits.cards.core.mappers.AccountActivationMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by deamor on 12.08.15.
 */
@Repository
public class AccountActivationRepository {

    private static final Logger LOG = Logger.getLogger(AccountActivationRepository.class);

    @Autowired
    private AccountActivationMapper activationMapper;

    public void save(final AccountActivation accountActivation) throws RepositoryException {
        if (accountActivation == null) {
            throw new RepositoryException("Null user received");
        }
        try {
            activationMapper.save(accountActivation);
        } catch (Exception e) {
            throw new RepositoryException("General database error" + e.getMessage(), e);
        }
    }

    public String findEmailByHash(String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("Hash is empty");
        }
        try {
            return activationMapper.findEmailByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("Find email by hash database error" + e.getMessage(), e);
        }
    }

    public void delete(final String email) throws RepositoryException{
        if (email == null) {
            throw new RepositoryException("email is empty in delete()");
        }
        try {
            activationMapper.delete(email);
        } catch (Exception e) {
            throw new RepositoryException("delete() database error" + e.getMessage(), e);
        }
    }

    public void updateHash(String newHash, String email) throws RepositoryException {
        try {
            activationMapper.updateHash(newHash, email);
        } catch (Exception e) {
            throw new RepositoryException("updateHash() repository error");
        }
    }

    public String findHashByEmail(final String email) throws RepositoryException {
        try {
            return activationMapper.findHashByEmail(email);
        } catch (Exception e) {
            throw new RepositoryException("findHashByEmail() repository error");
        }
    }

}
