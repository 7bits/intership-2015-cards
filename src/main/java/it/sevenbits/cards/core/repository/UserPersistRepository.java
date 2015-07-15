package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.mappers.UserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier(value = "discountPersistRepository")
public class UserPersistRepository implements UserRepository {
    private static Logger LOG = Logger.getLogger(UserPersistRepository.class);
    @Autowired
    private UserMapper mapper;
}