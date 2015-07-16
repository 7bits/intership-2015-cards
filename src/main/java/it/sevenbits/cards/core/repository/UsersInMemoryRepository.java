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
public class UsersInMemoryRepository implements UsersRepository {
    private final static Logger LOG = Logger.getLogger(UsersInMemoryRepository.class);

    private final Map<Long, User> users;
    private final AtomicLong keySequence;

    public UsersInMemoryRepository() {
        users = new HashMap<>();
        keySequence = new AtomicLong(1L);
    }
}