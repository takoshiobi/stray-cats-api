package ru.sds.straycats.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.mapper.PriceDBParamsMapper;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;

import java.sql.Timestamp;

@Repository
@AllArgsConstructor
public class PriceRepository {

    private static final String INSERT_CREATE_CAT_PRICE = """
            INSERT INTO straycats.price
            (cat_id, price, create_ts)
            VALUES (?, ?, ?)
            RETURNING id, cat_id, price, create_ts;
            """;

    private final PriceDBParamsMapper priceDBParamsMapper;
    private final JdbcTemplate jdbcTemplate;

    public PriceDBParamsDto create(final Long catId,
                                   final Double price,
                                   final Timestamp createTs
    ) {
        return jdbcTemplate.queryForObject(INSERT_CREATE_CAT_PRICE, priceDBParamsMapper,
                catId, price, createTs);
    }
}
