package ru.sds.straycats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sds.straycats.exception.BadRequestException;
import ru.sds.straycats.exception.NotFoundException;
import ru.sds.straycats.exception.ServerErrorException;
import ru.sds.straycats.model.dto.CatPriceDBParamsDto;
import ru.sds.straycats.model.dto.cat.*;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;
import ru.sds.straycats.repository.CatPriceRepository;
import ru.sds.straycats.repository.CatRepository;
import ru.sds.straycats.utils.StrayCatsUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final CatPriceRepository catPriceRepository;
    private final StrayCatsUtils strayCatsUtils;
    private final PriceService priceService;
    private static final List<Integer> genders = Arrays.asList(0, 1);
    Random rand = new Random();

    public Long createCat(CatDto catDto) {
        validateGender(catDto);

        if (Objects.isNull(catDto.getName()) || catDto.getName().isEmpty()) {
            catDto.setName(strayCatsUtils.generateName());
        }

        CatDBParamsDto createdCat = catRepository.create(
                catDto.getName(),
                catDto.getBirth(),
                catDto.getBreed(),
                catDto.getGender(),
                false
        );

        if (Objects.isNull(createdCat)) {
            throw new ServerErrorException("Cannot create cat");
        }

        priceService.createPrice(createdCat.getId(), catDto.getPrice());

        return createdCat.getId();
    }

    public CatGetByIdResponseDto getCatById(Long catId) {
        List<CatDBParamsDto> cat = catRepository.findById(catId);

        if (cat.isEmpty() || cat.getFirst().getRemovedFromSale().equals(true)) {
            throw new NotFoundException(String.format("Cat with id %s not found", catId));
        }

        return new CatGetByIdResponseDto()
                .setId(cat.getFirst().getId())
                .setBirth(cat.getFirst().getBirth())
                .setName(cat.getFirst().getName())
                .setBreed(cat.getFirst().getBreed())
                .setGender(cat.getFirst().getGender());
    }

    public CatDto updateCat(CatUpdateRequestDto catUpdateRequestDto) {
        if (catUpdateRequestDto.getName().equals("")) {
            throw new BadRequestException("Can't update cat name to empty string");
        }

        CatGetByIdResponseDto cat = getCatById(catUpdateRequestDto.getId());
        PriceDBParamsDto currentPrice = priceService.getCurrentPriceByCatId(catUpdateRequestDto.getId());

        catRepository.update(
                cat.getId(),
                catUpdateRequestDto.getName(),
                catUpdateRequestDto.getBirth(),
                catUpdateRequestDto.getBreed(),
                catUpdateRequestDto.getGender(),
                false
        );

        if (!currentPrice.getPrice().equals(catUpdateRequestDto.getPrice())) {
            priceService.createPrice(
                    cat.getId(),
                    catUpdateRequestDto.getPrice()
            );
        }

        return new CatDto(
                catUpdateRequestDto.getGender(),
                catUpdateRequestDto.getBirth(),
                catUpdateRequestDto.getBreed(),
                catUpdateRequestDto.getPrice()
        ).setName(catUpdateRequestDto.getName());
    }

    public void removeFromSale(CatDeleteRequestDto catDeleteRequestDto) {
        List<CatDBParamsDto> catList = catRepository.findById(catDeleteRequestDto.getId());
        if (catList.isEmpty()) {
            throw new NotFoundException(String.format("Cat with id %s not found", catDeleteRequestDto.getId()));
        }

        CatDBParamsDto cat = catList.getFirst();
        if (cat.getRemovedFromSale().equals(true)) {
            throw new BadRequestException(String.format("Cat with id %s has been already removed from sale", catDeleteRequestDto.getId()));
        }

        cat.setRemovedFromSale(true);

        catRepository.update(
                catDeleteRequestDto.getId(),
                cat.getName(),
                cat.getBirth(),
                cat.getBreed(),
                cat.getGender(),
                cat.getRemovedFromSale()
        );
    }

    public CatDto suggestCat(CatSuggestionRequestDto catSuggestionRequestDto) {
        List<CatPriceDBParamsDto> catList = catPriceRepository.getAllCatsByGenderAndMaxPrice(catSuggestionRequestDto.getGender(), catSuggestionRequestDto.getMaxPrice());

        if (catList.isEmpty()) {
            throw new NotFoundException("No cats to recommend");
        }

        if (catList.size() > 1) {
            CatPriceDBParamsDto suggestion = catList.get(rand.nextInt(catList.size()));
            return new CatDto(
                    suggestion.getGender(),
                    suggestion.getBirth(),
                    suggestion.getBreed(),
                    suggestion.getPrice()
            ).setName(suggestion.getName());
        }

        return new CatDto(
                catList.getFirst().getGender(),
                catList.getFirst().getBirth(),
                catList.getFirst().getBreed(),
                catList.getFirst().getPrice()
        ).setName(catList.getFirst().getName());
    }

    private void validateGender(CatDto catDto) {
        if (!genders.contains(catDto.getGender())) {
            throw new BadRequestException(String.format("Invalid gender %s. Cat gender should be binary: 0 - Female, 1 - Male", catDto.getGender()));
        }
    }
}
