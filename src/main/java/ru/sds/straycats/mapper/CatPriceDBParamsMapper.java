package ru.sds.straycats.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.sds.straycats.model.dto.CatPriceDBParamsDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CatPriceDBParamsMapper implements RowMapper<CatPriceDBParamsDto> {

    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_NAME = "name";
    private static final String FIELD_NAME_BIRTH = "birth_date";
    private static final String FIELD_NAME_BREED = "breed";
    private static final String FIELD_NAME_GENDER = "gender";
    private static final String FIELD_NAME_REMOVED_FROM_SALE = "removed_from_sale";
    private static final String FIELD_NAME_PRICE_ID = "id";
    private static final String FIELD_NAME_CAT_ID = "cat_id";
    private static final String FIELD_NAME_PRICE = "price";
    private static final String FIELD_NAME_CREATE_TS = "create_ts";

    @Override
    public CatPriceDBParamsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CatPriceDBParamsDto.builder()
                .id(rs.getLong(FIELD_NAME_ID))
                .name(rs.getString(FIELD_NAME_NAME))
                .birth(rs.getDate(FIELD_NAME_BIRTH))
                .breed(rs.getString(FIELD_NAME_BREED))
                .gender(rs.getInt(FIELD_NAME_GENDER))
                .removedFromSale(rs.getBoolean(FIELD_NAME_REMOVED_FROM_SALE))
                .priceId(rs.getLong(FIELD_NAME_PRICE_ID))
                .catId(rs.getLong(FIELD_NAME_CAT_ID))
                .price(rs.getDouble(FIELD_NAME_PRICE))
                .createTs(rs.getDate(FIELD_NAME_CREATE_TS))
                .build();
    }
}
