package ru.sds.straycats.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cat {

    @Schema(description = "Cat name")
    private String name;

    @Schema(description = "Cat gender (0 - Female, 1 - Male)")
    private Integer gender;

    @Schema(description = "Cat birthdate")
    private String birth;

    @Schema(description = "Cat breed")
    private String breed;

    @Schema(description = "Cat price")
    private Double price;
}
