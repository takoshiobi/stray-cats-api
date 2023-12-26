package ru.sds.straycats.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CatPriceDBParamsDto {

    private Long id;

    private String name;

    private LocalDate birth;

    private String breed;

    private Integer gender;

    private Boolean removedFromSale;

    private Long priceId;

    private Long catId;

    private Double price;

    private LocalDate createTs;
}
