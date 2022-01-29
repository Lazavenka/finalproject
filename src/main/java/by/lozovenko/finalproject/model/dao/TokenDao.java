package by.lozovenko.finalproject.model.dao;

import by.lozovenko.finalproject.exception.DaoException;
import by.lozovenko.finalproject.model.entity.Token;

import java.util.Optional;

public interface TokenDao extends BaseDao<Long, Token>{

    Optional<Token> findTokenByValue(String tokenValue) throws DaoException;
}
