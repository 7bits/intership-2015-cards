package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.DiscountHash;
import it.sevenbits.cards.core.mappers.DiscountHashMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by acoustics on 18.08.15.
 */
@Repository
public class DiscountHashRepository {

    private static final Logger LOG = Logger.getLogger(DiscountHashRepository.class);

    @Autowired
    private DiscountHashMapper discountHashMapper;

    public void save(final DiscountHash discountHash) throws RepositoryException {
        if (discountHash == null) {
            throw new RepositoryException("Null user received");
        }
        try {
            discountHashMapper.save(discountHash);
        } catch (Exception e) {
            throw new RepositoryException("General database error" + e.getMessage(), e);
        }
    }

    public void delete(final String hash) throws RepositoryException {
        if (hash == null) {
            throw new RepositoryException("discountId is empty in delete()");
        }
        try {
            discountHashMapper.delete(hash);
        } catch (Exception e) {
            throw new RepositoryException("delete() database error" + e.getMessage(), e);
        }
    }

    public Long findIdByHash(final String hash) throws RepositoryException {
        try {
            return discountHashMapper.findIdByHash(hash);
        } catch (Exception e) {
            throw new RepositoryException("discountHashMapper() repository error");
        }
    }
}


