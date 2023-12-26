package ru.sds.straycats.model.dto.cat;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CatDto {

    @Schema(description = "Cat name")
    private String name;

    @NonNull
    @Schema(description = "Cat gender (0 - Female, 1 - Male)")
    public Integer gender;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Schema(description = "Cat birthdate", type = "string", example = "12.12.2022")
    private LocalDate birth;

    @NonNull
    @Schema(description = "Cat breed")
    private String breed;

    @NonNull
    @Schema(description = "Cat price")
    private Double price;
}
