package ru.sds.straycats.model.dto.cat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class CatDto {

    @Schema(description = "Cat name")
    private String name;

    @NonNull
    @Schema(description = "Cat gender (0 - Female, 1 - Male)")
    private Integer gender;

    @NonNull
    @Schema(description = "Cat birthdate")
    private Date birth;

    @NonNull
    @Schema(description = "Cat breed")
    private String breed;

    @NonNull
    @Schema(description = "Cat price")
    private Double price;
}
