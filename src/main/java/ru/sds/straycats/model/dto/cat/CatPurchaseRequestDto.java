package ru.sds.straycats.model.dto.cat;

import lombok.Data;

@Data
public class CatPurchaseRequestDto {

    private Long catId;
    private Long purchaseId;
}
