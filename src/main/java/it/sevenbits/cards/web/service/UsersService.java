package it.sevenbits.cards.web.service;

import it.sevenbits.cards.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired

    @Qualifier(value = "userPersistRepository")
    private UserRepository repository;
}