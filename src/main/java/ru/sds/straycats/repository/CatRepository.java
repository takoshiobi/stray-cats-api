package ru.sds.straycats.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.mapper.CatDBParamsMapper;
import ru.sds.straycats.model.dto.cat.CatDBParamsDto;

import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class CatRepository {

    private static final String SELECT_FIND_BY_ID = """
            SELECT *
            FROM straycats.cat
            WHERE id = ?
            """;

    private static final String INSERT_CREATE_CAT = """
            INSERT INTO straycats.cat
            (name, birth_date, breed, gender, removed_from_sale)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id, name, birth_date, breed, gender, removed_from_sale;
            """;

    private static final String UPDATE_UPDATE_CAT = """
            UPDATE straycats.cat
            SET name = ?, birth_date = ?, breed = ?, gender = ?, removed_from_sale = ?
            WHERE id = ?;
            """;

    private final CatDBParamsMapper catDBParamsMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<CatDBParamsDto> findById(final Long id) {
        return jdbcTemplate.query(SELECT_FIND_BY_ID, catDBParamsMapper, id);
    }

    public CatDBParamsDto create(final String name,
                                 final Date birth,
                                 final String breed,
                                 final Integer gender,
                                 final Boolean removedFromSale
    ) {
        return jdbcTemplate.queryForObject(INSERT_CREATE_CAT, catDBParamsMapper,
                name, birth, breed, gender, removedFromSale);
    }

    public void update(final Long id,
                       final String name,
                       final Date birth,
                       final String breed,
                       final Integer gender,
                       final Boolean removedFromSale
    ) {
        jdbcTemplate.update(UPDATE_UPDATE_CAT,
                name, birth, breed, gender, removedFromSale, id);
    }
}
