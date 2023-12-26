package ru.sds.straycats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sds.straycats.exception.BadRequestException;
import ru.sds.straycats.exception.NotFoundException;
import ru.sds.straycats.exception.ServerErrorException;
import ru.sds.straycats.model.dto.CatPriceDBParamsDto;
import ru.sds.straycats.model.dto.cat.*;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;
import ru.sds.straycats.repository.CatPriceRepository;
import ru.sds.straycats.repository.CatRepository;

import static ru.sds.straycats.utils.StrayCatsUtils.*;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final CatPriceRepository catPriceRepository;
    private final PriceService priceService;

    @Transactional
    public Long createCat(CatDto catDto) {
        validateCatBeforeCreate(catDto);

        CatDBParamsDto createdCat = catRepository.create(
                catDto.getName(),
                catDto.getBirth(),
                catDto.getBreed(),
                catDto.getGender()
        );

        if (Objects.isNull(createdCat)) {
            throw new ServerErrorException("Cannot create cat");
        }

        priceService.createPrice(createdCat.getId(), catDto.getPrice());

        return createdCat.getId();
    }

    @Transactional(readOnly = true)
    public CatInfoWithoutPriceDto getCatById(Long catId) {
        List<CatDBParamsDto> cat = catRepository.findById(catId);

        if (cat.isEmpty() || cat.getFirst().getRemovedFromSale().equals(true)) {
            throw new NotFoundException(String.format("Cat with id %s not found", catId));
        }

        return new CatInfoWithoutPriceDto()
                .setId(cat.getFirst().getId())
                .setBirth(cat.getFirst().getBirth())
                .setName(cat.getFirst().getName())
                .setBreed(cat.getFirst().getBreed())
                .setGender(cat.getFirst().getGender());
    }

    @Transactional
    public CatCompleteInfoResponseDto updateCat(CatUpdateRequestDto catUpdateRequestDto) {
        CatInfoWithoutPriceDto cat = getCatById(catUpdateRequestDto.getId());

        if (Objects.nonNull(catUpdateRequestDto.getName())
                && !cat.getName().equals(catUpdateRequestDto.getName())) {

            if (catUpdateRequestDto.getName().isEmpty()) {
                throw new BadRequestException("Name cannot be empty string");
            }

            cat.setName(catUpdateRequestDto.getName());
        }

        if (Objects.nonNull(catUpdateRequestDto.getGender())
                && !cat.getGender().equals(catUpdateRequestDto.getGender())) {
            validateGender(catUpdateRequestDto.getGender());
            cat.setGender(catUpdateRequestDto.getGender());
        }

        if (Objects.nonNull(catUpdateRequestDto.getBirth())
                && !cat.getBirth().equals(catUpdateRequestDto.getBirth())) {
            validateBirthdate(catUpdateRequestDto.getBirth());
            cat.setBirth(catUpdateRequestDto.getBirth());
        }

        if (Objects.nonNull(catUpdateRequestDto.getBreed())
                && !cat.getBreed().equals(catUpdateRequestDto.getBreed())) {

            if (catUpdateRequestDto.getBreed().isEmpty()) {
                throw new BadRequestException("Breed cannot be empty string");
            }

            cat.setBreed(catUpdateRequestDto.getBreed());
        }

        if (Objects.nonNull(catUpdateRequestDto.getPrice())) {
            validatePrice(catUpdateRequestDto.getPrice());
            priceService.updatePrice(
                    cat.getId(),
                    catUpdateRequestDto.getPrice()
            );
        }

        CatDBParamsDto updatedCat = catRepository.update(cat);
        PriceDBParamsDto currentPrice = priceService.getCurrentPriceByCatId(cat.getId());

        return new CatCompleteInfoResponseDto()
                .setId(updatedCat.getId())
                .setName(updatedCat.getName())
                .setGender(updatedCat.getGender())
                .setBirth(updatedCat.getBirth())
                .setBreed(updatedCat.getBreed())
                .setPrice(currentPrice.getPrice());
    }

    @Transactional
    public void removeFromSale(CatDeleteRequestDto catDeleteRequestDto) {
        List<CatDBParamsDto> catList = catRepository.findById(catDeleteRequestDto.getId());
        if (catList.isEmpty()) {
            throw new NotFoundException(String.format("Cat with id %s not found", catDeleteRequestDto.getId()));
        }

        CatDBParamsDto cat = catList.getFirst();
        if (cat.getRemovedFromSale().equals(true)) {
            throw new BadRequestException(String.format("Cat with id %s has been already removed from sale", catDeleteRequestDto.getId()));
        }

        catRepository.removeFromSale(catDeleteRequestDto.getId());
    }

    @Transactional(readOnly = true)
    public CatCompleteInfoResponseDto suggestCat(CatSuggestionRequestDto catSuggestionRequestDto) {
        validatePrice(catSuggestionRequestDto.getMaxPrice());
        validateGender(catSuggestionRequestDto.getGender());

        CatPriceDBParamsDto suggestion;

        List<CatPriceDBParamsDto> catList = catPriceRepository.getAllCatsByGenderAndMaxPrice(catSuggestionRequestDto.getGender(), catSuggestionRequestDto.getMaxPrice());

        if (catList.isEmpty()) {
            throw new NotFoundException("No cats to recommend");
        }

        if (catList.size() > 1) {
            suggestion = getRandomCat(catList);
        } else {
            suggestion = catList.getFirst();
        }

        return new CatCompleteInfoResponseDto()
                .setId(suggestion.getId())
                .setName(suggestion.getName())
                .setBirth(suggestion.getBirth())
                .setBreed(suggestion.getBreed())
                .setGender(suggestion.getGender())
                .setPrice(suggestion.getPrice());
    }
}
