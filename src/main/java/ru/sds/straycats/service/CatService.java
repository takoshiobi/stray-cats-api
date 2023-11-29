package ru.sds.straycats.service;

import lombok.RequiredArgsConstructor;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;


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

        validateCat(cat);

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

        return catInfoMapper.toDto(cat);
    }

    public List<PriceInfo> getCatPriceHistory(Long catId) {
        List<PriceEntity> catPrices = priceRepository.getPriceEntitiesByCatId(catId);
        return catPrices
                .stream()
                .map(catPrice -> priceInfoMapper.toDto(catPrice))
                .collect(Collectors.toList());
    }

    private void validateCat(Cat cat) {
        if (!cat.getBirth().matches(VALID_DATE_FORMAT)) {
            throw new BadRequestException("Birth date format must comply pattern DD.MM.YYYY");
        }
        if (Objects.isNull(cat.getBirth())
                || Objects.isNull(cat.getGender())
                || Objects.isNull(cat.getPrice())
                || Objects.isNull(cat.getBreed())) {
            throw new BadRequestException("Missing argument(-s): birth, gender, price or breed");
        }
        if (!genders.contains(cat.getGender())) {
            throw new BadRequestException("Cat gender should be binary: 0 - Female, 1 - Male");
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
