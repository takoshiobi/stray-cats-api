package ru.sds.straycats.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatSuggest {

    @Schema(description = "Cat gender")
    private Integer gender;

    @Schema(description = "Max price")
    private Double maxPrice;
}