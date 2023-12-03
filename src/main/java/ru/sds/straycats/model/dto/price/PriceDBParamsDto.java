package ru.sds.straycats.model.dto.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class PriceDBParamsDto {

    @Id
    private Long id;

    private Long catId;

    private Double price;

    private Timestamp createTs;
}
