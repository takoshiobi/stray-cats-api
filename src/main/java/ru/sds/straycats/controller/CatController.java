package ru.sds.straycats.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sds.straycats.annotation.ObjectResponse;
import ru.sds.straycats.model.dto.cat.*;
import ru.sds.straycats.service.CatService;

@Tag(name = "Cats")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cats")
public class CatController {

    private final CatService catService;

    @ObjectResponse
    @Operation(summary = "Create cat")
    @PostMapping
    public Long create(@RequestBody CatDto catDto) {
        return catService.createCat(catDto);
    }

    @ObjectResponse
    @Operation(summary = "Get cat by id")
    @GetMapping(value = "/{id}")
    public CatInfoWithoutPriceDto getCat(@PathVariable Long id) {
        return catService.getCatById(id);
    }

    @ObjectResponse
    @Operation(summary = "Update cat information")
    @PutMapping
    public CatCompleteInfoResponseDto updateCat(@RequestBody CatUpdateRequestDto catUpdateRequestDto) {
        return catService.updateCat(catUpdateRequestDto);
    }

    @ObjectResponse
    @Operation(summary = "Remove cat from sale")
    @DeleteMapping
    public void removeFromSale(@RequestBody CatDeleteRequestDto catDeleteRequestDto) {
        catService.removeFromSale(catDeleteRequestDto);
    }

    @ObjectResponse
    @Operation(summary = "Suggest cat")
    @PostMapping(value = "/suggest")
    public CatCompleteInfoResponseDto suggestCat(@RequestBody CatSuggestionRequestDto catSuggestionRequestDto) {
        return catService.suggestCat(catSuggestionRequestDto);
    }
}
