package ru.sds.straycats.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sds.straycats.annotation.ObjectResponse;
import ru.sds.straycats.model.dto.price.PriceCurrentResponseDto;
import ru.sds.straycats.model.dto.price.PriceHistoryResponseDto;
import ru.sds.straycats.service.PriceService;

import java.util.List;

@Tag(name = "Prices")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/price")
public class PriceController {

    private final PriceService priceService;

    @ObjectResponse
    @Operation(summary = "Get cat current price by cat id")
    @GetMapping(value = "/{catId}")
    public PriceCurrentResponseDto getCurrentCatPrice(@PathVariable Long catId) {
        return priceService.getCurrentPrice(catId);
    }

    @ObjectResponse
    @Operation(summary = "Get cat price history by cat id")
    @GetMapping(value = "/{catId}/history")
    public List<PriceHistoryResponseDto> getCatPriceHistory(@PathVariable Long catId) {
        return priceService.getCatPriceHistory(catId);
    }
}
