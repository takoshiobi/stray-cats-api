package ru.sds.straycats.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sds.straycats.mapper.CatDBParamsMapper;
import ru.sds.straycats.model.dto.cat.CatDBParamsDto;
import ru.sds.straycats.model.dto.cat.CatInfoWithoutPriceDto;

import java.time.LocalDate;
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
            VALUES (?, ?, ?, ?, false)
            RETURNING id, name, birth_date, breed, gender, removed_from_sale;
            """;

    private static final String UPDATE_UPDATE_CAT = """
            UPDATE straycats.cat
            SET name = ?, birth_date = ?, breed = ?, gender = ?
            WHERE id = ?
            RETURNING id, name, birth_date, breed, gender, removed_from_sale;
            """;

    private static final String UPDATE_REMOVE_FROM_SALE = """
            UPDATE straycats.cat
            SET removed_from_sale = true
            WHERE id = ?;
            """;

    private final CatDBParamsMapper catDBParamsMapper;
    private final JdbcTemplate jdbcTemplate;

    public List<CatDBParamsDto> findById(final Long id) {
        return jdbcTemplate.query(SELECT_FIND_BY_ID, catDBParamsMapper, id);
    }

    public CatDBParamsDto create(final String name,
                                 final LocalDate birth,
                                 final String breed,
                                 final Integer gender
    ) {
        return jdbcTemplate.queryForObject(INSERT_CREATE_CAT, catDBParamsMapper,
                name, birth, breed, gender);
    }

    public CatDBParamsDto update(CatInfoWithoutPriceDto catInfoWithoutPriceDto) {
        return jdbcTemplate.queryForObject(UPDATE_UPDATE_CAT, catDBParamsMapper,
                catInfoWithoutPriceDto.getName(),
                catInfoWithoutPriceDto.getBirth(),
                catInfoWithoutPriceDto.getBreed(),
                catInfoWithoutPriceDto.getGender(),
                catInfoWithoutPriceDto.getId());
    }

    public void removeFromSale(final Long id) {
        jdbcTemplate.update(UPDATE_REMOVE_FROM_SALE, id);
    }
}
