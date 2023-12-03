package ru.sds.straycats.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.mapper.PriceDBParamsMapper;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;

import java.sql.Timestamp;
import java.util.List;

@Repository
@AllArgsConstructor
public class PriceRepository {

    private static final String SELECT_GET_CAT_CURRENT_PRICE = """
            SELECT *
            FROM straycats.price
            WHERE cat_id = ?
            ORDER BY create_ts DESC
            LIMIT 1
            """;

    private static final String SELECT_GET_CAT_PRICE_HISTORY = """
            SELECT *
            FROM straycats.price
            WHERE cat_id = ?
            ORDER BY create_ts DESC
            """;

    private static final String INSERT_CREATE_CAT_PRICE = """
            INSERT INTO straycats.price
            (cat_id, price, create_ts)
            VALUES (?, ?, ?)
            RETURNING id, cat_id, price, create_ts;
            """;

    private final PriceDBParamsMapper priceDBParamsMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<PriceDBParamsDto> getCatCurrentPrice(final Long catId) {
        return jdbcTemplate.query(SELECT_GET_CAT_CURRENT_PRICE, priceDBParamsMapper, catId);
    }

    public List<PriceDBParamsDto> getCatPriceHistory(final Long catId) {
        return jdbcTemplate.query(SELECT_GET_CAT_PRICE_HISTORY, priceDBParamsMapper, catId);
    }

    public PriceDBParamsDto create(final Long catId,
                                   final Double price,
                                   final Timestamp createTs
    ) {
        return jdbcTemplate.queryForObject(INSERT_CREATE_CAT_PRICE, priceDBParamsMapper,
                catId, price, createTs);
    }
}
