package ru.sds.straycats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sds.straycats.exception.BadRequestException;
import ru.sds.straycats.exception.NotFoundException;
import ru.sds.straycats.model.dto.price.PriceCurrentResponseDto;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;
import ru.sds.straycats.model.dto.price.PriceHistoryResponseDto;
import ru.sds.straycats.repository.CatPriceRepository;
import ru.sds.straycats.repository.PriceRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;
    private final CatPriceRepository catPriceRepository;

    @Transactional
    public void createPrice(Long catId, Double price) {
        priceRepository.create(
                catId,
                price,
                new Timestamp(System.currentTimeMillis())
        );
    }

    @Transactional
    public void updatePrice(Long catId, Double price) {
        PriceDBParamsDto currentPrice = getCurrentPriceByCatId(catId);

        if (!currentPrice.getPrice().equals(price)) {
            priceRepository.create(
                    catId,
                    price,
                    new Timestamp(System.currentTimeMillis())
            );
        }
    }

    @Transactional(readOnly = true)
    public PriceDBParamsDto getCurrentPriceByCatId(Long catId) {
        List<PriceDBParamsDto> priceList = catPriceRepository.getCatCurrentPrice(catId);

        if (priceList.isEmpty()) {
            throw new BadRequestException(String.format("Price for cat with id %s not found", catId));
        }

        return priceList.getFirst();
    }

    @Transactional(readOnly = true)
    public PriceCurrentResponseDto getCurrentPrice(Long catId) {
        PriceDBParamsDto priceDBParamsDto = getCurrentPriceByCatId(catId);

        return new PriceCurrentResponseDto().
                setCatId(catId)
                .setPrice(priceDBParamsDto.getPrice())
                .setCreateTs(priceDBParamsDto.getCreateTs());
    }

    @Transactional(readOnly = true)
    public List<PriceHistoryResponseDto> getCatPriceHistory(Long catId) {
        List<PriceDBParamsDto> priceHistory = catPriceRepository.getCatPriceHistory(catId);

        if (priceHistory.isEmpty()) {
            throw new NotFoundException((String.format("Price history for cat with id %s not found", catId)));
        }

        return priceHistory
                .stream()
                .map(price -> new PriceHistoryResponseDto()
                                .setPrice(price.getPrice())
                                .setCreateTs(price.getCreateTs()))
                .toList();
    }
}
