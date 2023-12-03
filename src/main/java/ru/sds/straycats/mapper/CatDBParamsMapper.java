package ru.sds.straycats.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.sds.straycats.model.dto.cat.CatDBParamsDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CatDBParamsMapper implements RowMapper<CatDBParamsDto> {

    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_NAME = "name";
    private static final String FIELD_NAME_BIRTH = "birth_date";
    private static final String FIELD_NAME_BREED = "breed";
    private static final String FIELD_NAME_GENDER = "gender";
    private static final String FIELD_NAME_REMOVED_FROM_SALE = "removed_from_sale";

    @Override
    public CatDBParamsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CatDBParamsDto.builder()
                .id(rs.getLong(FIELD_NAME_ID))
                .name(rs.getString(FIELD_NAME_NAME))
                .birth(rs.getDate(FIELD_NAME_BIRTH))
                .breed(rs.getString(FIELD_NAME_BREED))
                .gender(rs.getInt(FIELD_NAME_GENDER))
                .removedFromSale(rs.getBoolean(FIELD_NAME_REMOVED_FROM_SALE))
                .build();
    }
}
