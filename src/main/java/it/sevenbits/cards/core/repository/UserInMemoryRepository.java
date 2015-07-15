package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Qualifier(value = "userInMemoryRepository")
public class UserInMemoryRepository implements UserRepository {
    private final static Logger LOG = Logger.getLogger(UserInMemoryRepository.class);

    private final Map<Long, User> users;
    private final AtomicLong keySequence;

    public UserInMemoryRepository() {
        users = new HashMap<>();
        keySequence = new AtomicLong(1L);
    }
}