package ru.sds.straycats.model.dto.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceHistoryResponseDto {

    @Schema(description = "Cat price")
    private Double price;

    @Schema(description = "Price creation time")
    private Timestamp createTs;
}
