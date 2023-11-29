package ru.sds.straycats.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sds.straycats.annotation.CreateResponse;
import ru.sds.straycats.model.dto.Cat;
import ru.sds.straycats.model.dto.CatInfo;
import ru.sds.straycats.service.CatService;


@Tag(name = "Stray cats")
@RequiredArgsConstructor
@RestController
@ControllerAdvice
@RequestMapping("/api/v1/cats")
public class CatController {

    private final CatService catService;

    @CreateResponse
    @Operation(summary = "Create cat")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CatInfo create(@RequestBody Cat cat) {
        return catService.createCat(cat);
    }
}
