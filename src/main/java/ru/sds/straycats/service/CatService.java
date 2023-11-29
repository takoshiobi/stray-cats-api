package ru.sds.straycats.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import ru.sds.straycats.exception.BadRequestException;
import ru.sds.straycats.exception.NotFoundException;
import ru.sds.straycats.mapper.CatInfoMapper;
import ru.sds.straycats.mapper.CatMapper;
import ru.sds.straycats.mapper.PriceInfoMapper;
import ru.sds.straycats.model.dto.Cat;
import ru.sds.straycats.model.dto.CatInfo;
import ru.sds.straycats.model.dto.PriceInfo;
import ru.sds.straycats.model.entity.CatEntity;
import ru.sds.straycats.model.entity.PriceEntity;
import ru.sds.straycats.repository.CatRepository;
import ru.sds.straycats.repository.PriceRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository catRepository;
    private final PriceRepository priceRepository;
    private final CatMapper catMapper;
    private final CatInfoMapper catInfoMapper;
    private final PriceInfoMapper priceInfoMapper;

    private static final String VALID_DATE_FORMAT = "^[0-3]{0,1}[0-9][.][0-1]{0,1}[0-9][.][0-9]{4}$";
    private static final List<Integer> genders = Arrays.asList(0, 1);
    Random rand = new Random();

    public CatInfo createCat(Cat cat) {
        if (Objects.isNull(cat.getName()) || cat.getName().isEmpty()) {
            cat.setName(generateName());
        }

        validateNullsCat(cat);
        validateGender(cat);
        validateBirthDate(cat);

        CatEntity catEntity = catMapper.toEntity(cat);
        catRepository.save(catEntity);

        CatEntity createdCat = catRepository.findById(catEntity.getId())
                .orElseThrow(() -> new NotFoundException("Cat not found"));

        PriceEntity price = new PriceEntity();
        price.setCatId(createdCat.getId());
        price.setPrice(cat.getPrice());
        price.setCreateTs(LocalDateTime.now());
        priceRepository.save(price);

        return catInfoMapper.toDto(createdCat);
    }

    public CatInfo getCatById(Long catId) {
        CatEntity cat = catRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(String.format("Cat with id %s doesn't exist", catId)));

        if (cat.getRemovedFromSale()) {
            throw new NotFoundException("Cat has been removed from sale");
        }

        return catInfoMapper.toDto(cat);
    }

    public List<PriceInfo> getPriceHistoryByCatId(Long catId) {
        List<PriceEntity> catPrices = priceRepository.getPriceEntitiesByCatIdOrderByCreateTsDesc(catId);
        return catPrices
                .stream()
                .map(priceInfoMapper::toDto)
                .toList();
    }

    public PriceInfo getCurrentPriceByCatId(Long catId) {
        PriceEntity priceEntity = priceRepository.findByCatIdAndMaxCreateTs(catId);

        if (ObjectUtils.isEmpty(priceEntity)) {
            throw new NotFoundException("Цена не найдена: бесценный кот");
        }

        return priceInfoMapper.toDto(priceEntity);
    }

    public CatInfo patchCatInfo(Long catId, Cat cat) {
        CatEntity currentCat = catRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Cat not found"));

        PriceEntity priceEntity = new PriceEntity();

        if (Objects.nonNull(cat.getPrice())) {
            priceEntity.setCatId(catId);
            priceEntity.setPrice(cat.getPrice());
            priceEntity.setCreateTs(LocalDateTime.now());
            priceRepository.save(priceEntity);
        }

        if (Objects.nonNull(cat.getBirth())) {
            validateBirthDate(cat);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            try {
                currentCat.setBirth(format.parse(cat.getBirth()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if (Objects.nonNull(cat.getGender())) {
            validateGender(cat);
            currentCat.setGender(cat.getGender());
        }

        if (Objects.nonNull(cat.getName())) {
            if (cat.getName().isEmpty()) {
                currentCat.setName(generateName());
            }
            currentCat.setName(cat.getName());
        }

        if (Objects.nonNull(cat.getBreed())) {
            currentCat.setBreed(cat.getBreed());
        }

        catRepository.save(currentCat);
        return catInfoMapper.toDto(currentCat);
    }

    private void validateNullsCat(Cat cat) {
        if (Objects.isNull(cat.getBirth())
                || Objects.isNull(cat.getGender())
                || Objects.isNull(cat.getPrice())
                || Objects.isNull(cat.getBreed())) {
            throw new BadRequestException("Missing argument(-s): birth, gender, price or breed");
        }
    }

    private void validateGender(Cat cat) {
        if (!genders.contains(cat.getGender())) {
            throw new BadRequestException("Cat gender should be binary: 0 - Female, 1 - Male");
        }
    }

    private void validateBirthDate(Cat cat) {
        if (!cat.getBirth().matches(VALID_DATE_FORMAT)) {
            throw new BadRequestException("Birth date format must comply pattern DD.MM.YYYY");
        }
    }

    private String generateName() {
        List<String> firstName = Arrays.asList("Fluffy", "Dummy", "Orange", "Sparkling", "Extra large");
        List<String> lastName = Arrays.asList("Pumpkin", "Disaster", "Monsieur", "Weirdo", "Shine");

        return firstName.get(rand.nextInt(firstName.size()))
                + " "
                + lastName.get(rand.nextInt(lastName.size()));
    }
}
