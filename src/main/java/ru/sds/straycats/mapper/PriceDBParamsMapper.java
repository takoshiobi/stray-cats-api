package ru.sds.straycats.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PriceDBParamsMapper implements RowMapper<PriceDBParamsDto> {

    private static final String FIELD_NAME_ID = "id";
    private static final String FIELD_NAME_CAT_ID = "cat_id";
    private static final String FIELD_NAME_PRICE = "price";
    private static final String FIELD_NAME_CREATE_TS = "create_ts";

    @Override
    public PriceDBParamsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PriceDBParamsDto.builder()
                .id(rs.getLong(FIELD_NAME_ID))
                .catId(rs.getLong(FIELD_NAME_CAT_ID))
                .price(rs.getDouble(FIELD_NAME_PRICE))
                .createTs(rs.getTimestamp(FIELD_NAME_CREATE_TS))
                .build();
    }
}
