package ru.sds.straycats.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.sds.straycats.model.dto.Cat;
import ru.sds.straycats.model.entity.CatEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface CatMapper {

    @Named("transformDate")
    static Date transformDate(String dateString) throws ParseException {
        if (Objects.nonNull(dateString)) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            return format.parse(dateString);
        }
        return null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name")
    @Mapping(target = "birth", source = "birth", qualifiedByName = "transformDate")
    @Mapping(target = "breed")
    @Mapping(target = "gender")
    @Mapping(target = "removedFromSale", constant = "false")
    CatEntity toEntity(Cat cat);
}
