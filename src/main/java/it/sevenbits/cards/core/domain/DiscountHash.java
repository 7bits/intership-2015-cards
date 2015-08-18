package it.sevenbits.cards.core.domain;

import java.io.Serializable;

public class DiscountHash implements Serializable {
    private Long discountId;
    private String hash;

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return String.format("AccountActivation[discountId=%d, hash=%s]",
                discountId, hash);
    }

}


