package it.sevenbits.cards.core.repository;

import it.sevenbits.cards.core.domain.Discount;
import it.sevenbits.cards.core.repository.DiscountRepository;
import it.sevenbits.cards.core.repository.RepositoryException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Qualifier(value = "discountInMemoryRepository")
public class DiscountInMemoryRepository implements DiscountRepository {
    private final static Logger LOG = Logger.getLogger(DiscountInMemoryRepository.class);

    private final Map<Long, Discount> discounts;
    private final AtomicLong keySequence;

    public DiscountInMemoryRepository() {
        discounts = new HashMap<>();
        keySequence = new AtomicLong(1L);
    }

    @Override
    public void save(final Discount discount) throws RepositoryException {
        if (discount == null) {
            LOG.error("Discount is null");
            throw new RepositoryException("Discount is null");
        }
        LOG.info("Start saving: " + discount.toString());
        discount.setId(keySequence.getAndIncrement());
        discounts.put(discount.getId(), discount);
        LOG.info("Saved: " + discount.toString());
    }

    @Override
    public void delete(final Discount discount) throws RepositoryException {
        if (discount == null) {
            LOG.error("Discount is null");
            throw new RepositoryException("Discount is null");
        }
        LOG.info("Start deleting: " + discount.toString());
    }
    @Override
    public List<Discount> findAll() {
        return new ArrayList<>(discounts.values());
    }

    @Override
    public List<Discount> findAllDiscountsToUse() {
        return new ArrayList<>(discounts.values());
    }

    @Override
    public List<Discount> findAllDiscountsToSend() {
        return new ArrayList<>(discounts.values());
    }

}