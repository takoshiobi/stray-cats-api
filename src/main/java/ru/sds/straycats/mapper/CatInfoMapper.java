package ru.sds.straycats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sds.straycats.model.dto.CatInfo;
import ru.sds.straycats.model.entity.CatEntity;

@Mapper(componentModel = "spring")
public interface CatInfoMapper {

    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "breed")
    @Mapping(target = "birth")
    @Mapping(target = "gender")
    CatInfo toDto(CatEntity cat);
}
