package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.domain.AccountActivation;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.core.repository.AccountActivationRepository;
import it.sevenbits.cards.core.repository.RepositoryException;
import it.sevenbits.cards.core.repository.UserRepository;
import it.sevenbits.cards.validation.Sender;
import it.sevenbits.cards.validation.Sha;
import it.sevenbits.cards.web.domain.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by deamor on 12.08.15.
 */
@Service
public class AccountActivationService {

    @Autowired
    private AccountActivationRepository activationRepository;

    @Autowired
    private UserRepository userRepository;

    private static Sender sender = new Sender();

    Logger LOG = Logger.getLogger(AccountActivationService.class);

    User user = new User();

    public void printHash(String hash) {
        LOG.info(hash);
    }


    public AccountActivation generateActivationHash(RegistrationForm form) throws ServiceException{
        try {
            user = userRepository.findByUsername(form.getEmail());
        } catch (RepositoryException e) {
            LOG.error("user doesn't exist1");
        }
        if (user == null) {
            LOG.error("user doesn't exist");
            return null;
        } else {
            AccountActivation activation = new AccountActivation();
            activation.setEmail(form.getEmail());
            try {
                activation.setHash(Sha.hash256());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                if(activationRepository.findHashByEmail(activation.getEmail()) == null) {
                    activationRepository.save(activation);
                } else {
                    activationRepository.updateHash(activation.getHash(), activation.getEmail());
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            return activation;
        }
    }
    @Async
    public void sendEmail(AccountActivation accountActivation) throws ServiceException {
        if (accountActivation == null) {
            LOG.error("User doesn't exist");
        } else {
            sender.send("Активация аккаунта Discounts", "Ссылка для активации:\n" +
                    "http://discounts.7bits.it/registration/?hash=" + accountActivation.getHash(), accountActivation.getEmail());
        }
    }
    public void activateByHash(String hash) {
        String email = null;
        try {
            email = activationRepository.findEmailByHash(hash);
        } catch (RepositoryException e) {
            LOG.error("Activation finding email by hash exception.");
        }
        try {
            LOG.info(email);
            activationRepository.delete(email);
            userRepository.enableUserByEmail(email);
        } catch (RepositoryException e) {
            LOG.error("Activation deleting exception");
        }
    }
    public String findEmailByHash(String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("hash is empty");
        }
        try {
            return activationRepository.findEmailByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("Find email by hash database error" + e.getMessage(), e);
        }
    }

}
