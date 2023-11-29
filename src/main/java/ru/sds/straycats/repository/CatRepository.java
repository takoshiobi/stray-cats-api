package ru.sds.straycats.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.model.entity.CatEntity;

@Repository
public interface CatRepository extends CrudRepository<CatEntity, Long> {

}