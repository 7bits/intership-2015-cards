package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.mappers.UsersMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "discountPersistRepository")
public class UsersPersistRepository implements UsersRepository {
    private static Logger LOG = Logger.getLogger(UsersPersistRepository.class);
    @Autowired
    private UsersMapper mapper;
}