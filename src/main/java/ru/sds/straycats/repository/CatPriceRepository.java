package ru.sds.straycats.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.mapper.CatPriceDBParamsMapper;
import ru.sds.straycats.mapper.PriceDBParamsMapper;
import ru.sds.straycats.model.dto.CatPriceDBParamsDto;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;

import java.util.List;

@Repository
@AllArgsConstructor
public class CatPriceRepository {

    private static final String SELECT_GET_ALL_BY_GENDER_AND_MAX_PRICE = """
            SELECT cat.*, price.*
            FROM straycats.price price
            JOIN (
                SELECT cat_id, max(create_ts), max(id) AS id
                FROM straycats.price
                GROUP BY cat_id
            ) max_price ON price.id = max_price.id
            JOIN straycats.cat cat ON price.cat_id = cat.id
            WHERE cat.removed_from_sale = false
            AND cat.gender = ?
            AND price.price < ?;
            """;

    private static final String SELECT_GET_CAT_PRICE_HISTORY = """
            SELECT *
            FROM straycats.price p
            JOIN straycats.cat c ON c.id = p.cat_id
            WHERE p.cat_id = ?
            AND c.removed_from_sale = false
            ORDER BY p.create_ts DESC
            """;

    private static final String SELECT_GET_CAT_CURRENT_PRICE = """
            SELECT *
            FROM straycats.price p
            JOIN straycats.cat c ON c.id = p.cat_id
            WHERE p.cat_id = ?
            AND c.removed_from_sale = false
            ORDER BY p.create_ts DESC
            LIMIT 1
            """;

    private final CatPriceDBParamsMapper catPriceDBParamsMapper;
    private final PriceDBParamsMapper priceDBParamsMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<CatPriceDBParamsDto> getAllCatsByGenderAndMaxPrice(final Integer gender, final Double price) {
        return jdbcTemplate.query(SELECT_GET_ALL_BY_GENDER_AND_MAX_PRICE, catPriceDBParamsMapper, gender, price);
    }

    public List<PriceDBParamsDto> getCatPriceHistory(final Long catId) {
        return jdbcTemplate.query(SELECT_GET_CAT_PRICE_HISTORY, priceDBParamsMapper, catId);
    }

    public List<PriceDBParamsDto> getCatCurrentPrice(final Long catId) {
        return jdbcTemplate.query(SELECT_GET_CAT_CURRENT_PRICE, priceDBParamsMapper, catId);
    }
}
