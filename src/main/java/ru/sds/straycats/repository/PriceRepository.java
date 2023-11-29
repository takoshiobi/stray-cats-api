package ru.sds.straycats.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.model.entity.PriceEntity;

import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long> {
    List<PriceEntity> getPriceEntitiesByCatIdOrderByCreateTsDesc(Long id);
}
