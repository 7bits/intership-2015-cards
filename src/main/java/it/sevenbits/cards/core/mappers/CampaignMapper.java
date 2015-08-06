package it.sevenbits.cards.core.mappers;
import it.sevenbits.cards.core.domain.Campaign;
import it.sevenbits.cards.core.domain.Discount;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface CampaignMapper {
    //Save
    @Insert("INSERT INTO campaign (store_name, name, description, percent, enabled) VALUES (#{storeName}, #{name}, #{description}, #{percent}, #{enabled})")
    void save(final Campaign campaign);
}