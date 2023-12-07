package ru.sds.straycats.model.dto.cat;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatSuggestionRequestDto {

    @NonNull
    @Schema(description = "Cat gender")
    private Integer gender;

    @NonNull
    @Schema(description = "Max price")
    private Double maxPrice;
}