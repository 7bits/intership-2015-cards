package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.domain.User;
import it.sevenbits.cards.web.domain.UserForm;

import java.util.Collection;
import java.util.List;

public interface UsersRepository{
    void saveUser(final User user) throws RepositoryException;
}

