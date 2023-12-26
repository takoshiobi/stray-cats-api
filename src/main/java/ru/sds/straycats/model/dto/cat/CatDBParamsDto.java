package ru.sds.straycats.model.dto.cat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CatDBParamsDto {

    @Id
    private Long id;

    private String name;

    private LocalDate birth;

    private String breed;

    private Integer gender;

    private Boolean removedFromSale;
}
