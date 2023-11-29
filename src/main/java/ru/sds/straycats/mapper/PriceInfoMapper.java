package ru.sds.straycats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sds.straycats.model.dto.PriceInfo;
import ru.sds.straycats.model.entity.PriceEntity;

@Mapper(componentModel = "spring")
public interface PriceInfoMapper {

    @Mapping(target = "id")
    @Mapping(target = "catId")
    @Mapping(target = "price")
    @Mapping(target = "createTs")
    PriceInfo toDto(PriceEntity price);
}