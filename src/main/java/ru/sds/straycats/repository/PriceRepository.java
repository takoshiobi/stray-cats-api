package ru.sds.straycats.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.model.entity.PriceEntity;

import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long> {

    List<PriceEntity> getPriceEntitiesByCatIdOrderByCreateTsDesc(Long id);

    @Query(value = "" +
            "select * " +
            "from straycats.price p " +
            "where p.cat_id = :id " +
            "order by p.create_ts desc " +
            "limit 1"
    )
    PriceEntity findByCatIdAndMaxCreateTs(Long id);
}
