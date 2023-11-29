package ru.sds.straycats.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceInfo {

    @Schema(description = "Price id")
    private Long id;

    @Schema(description = "Cat id")
    private Long catId;

    @Schema(description = "Price")
    private Double price;

    @Schema(description = "Create timestamp")
    private LocalDateTime createTs;
}
