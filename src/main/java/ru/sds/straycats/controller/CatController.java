package ru.sds.straycats.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sds.straycats.annotation.CreateResponse;
import ru.sds.straycats.annotation.ObjectResponse;
import ru.sds.straycats.model.dto.Cat;
import ru.sds.straycats.model.dto.CatInfo;
import ru.sds.straycats.model.dto.PriceInfo;
import ru.sds.straycats.service.CatService;

import java.util.List;

@Tag(name = "Stray cats")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cats")
public class CatController {

    private final CatService catService;

    @CreateResponse
    @Operation(summary = "Create cat")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CatInfo create(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }

    @ObjectResponse
    @Operation(summary = "Get cat by id")
    @GetMapping(value = "/{id}")
    public CatInfo getCat(@PathVariable Long id) {
        return catService.getCatById(id);
    }

    @ObjectResponse
    @Operation(summary = "Get cat price history")
    @GetMapping(value = "/{id}/price/history")
    public List<PriceInfo> getCatPriceHistory(@PathVariable Long id) {
        return catService.getPriceHistoryByCatId(id);
    }

    @ObjectResponse
    @Operation(summary = "Update cat information")
    @PatchMapping(value = "/{id}")
    public CatInfo patchCatInfo(@PathVariable Long id, @RequestBody Cat cat) {
        return catService.patchCatInfo(id, cat);
    }

    @ObjectResponse
    @Operation(summary = "Get current price of the cat")
    @GetMapping(value = "/{id}/price")
    public PriceInfo getCurrentCatPrice(@PathVariable Long id) {
        return catService.getCurrentPriceByCatId(id);
    }

    @ObjectResponse
    @Operation(summary = "Remove kitty from sale")
    @DeleteMapping(value = "/{id}")
    public String removeFromSale(@PathVariable Long id) {
        return catService.removeFromSale(id);
    }
}
