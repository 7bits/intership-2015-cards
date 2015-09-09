package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Account;
import it.sevenbits.cards.core.mappers.AccountMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    private static final Logger LOG = Logger.getLogger(AccountRepository.class);

    @Autowired
    private AccountMapper activationMapper;

    public void save(final Account account) throws RepositoryException {
        if (account == null) {
            throw new RepositoryException("Null user received");
        }
        try {
            activationMapper.save(account);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while saving account:" + e.getMessage(), e);
        }
    }

    public String findEmailByHash(String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("Hash is empty");
        }
        try {
            return activationMapper.findEmailByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while finding email by hash:" + e.getMessage(), e);
        }
    }

    public void delete(final String email) throws RepositoryException{
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            activationMapper.delete(email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while deleting account hash by email:" + e.getMessage(), e);
        }
    }

    public void updateHash(String hash, String email) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("Hash is null");
        }
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            activationMapper.updateHash(hash, email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while updating account hash:" + e.getMessage(), e);
        }
    }

    public String findHashByEmail(final String email) throws RepositoryException {
        if (email == null) {
            throw new RepositoryException("Email is null");
        }
        try {
            return activationMapper.findHashByEmail(email);
        } catch (Exception e) {
            throw new RepositoryException("An error occurred while finding hash by email:" + e.getMessage(), e);
        }
    }

}
