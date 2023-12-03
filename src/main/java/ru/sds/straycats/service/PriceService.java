package ru.sds.straycats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sds.straycats.exception.BadRequestException;
import ru.sds.straycats.exception.NotFoundException;
import ru.sds.straycats.model.dto.price.PriceCurrentResponseDto;
import ru.sds.straycats.model.dto.price.PriceDBParamsDto;
import ru.sds.straycats.model.dto.price.PriceHistoryResponseDto;
import ru.sds.straycats.repository.PriceRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceDBParamsDto createPrice(Long catId, Double price) {
        return priceRepository.create(
                catId,
                price,
                new Timestamp(System.currentTimeMillis())
        );
    }

    public PriceDBParamsDto getCurrentPriceByCatId(Long catId) {
        List<PriceDBParamsDto> priceList = priceRepository.getCatCurrentPrice(catId);

        if (priceList.isEmpty()) {
            throw new BadRequestException(String.format("Price for cat with id %s not found", catId));
        }

        return priceList.getFirst();
    }

    public PriceCurrentResponseDto getCurrentPrice(Long catId) {
        PriceDBParamsDto priceDBParamsDto = getCurrentPriceByCatId(catId);

        return new PriceCurrentResponseDto().
                setCatId(catId)
                .setPrice(priceDBParamsDto.getPrice())
                .setCreateTs(priceDBParamsDto.getCreateTs());
    }

    public List<PriceHistoryResponseDto> getCatPriceHistory(Long catId) {
        List<PriceDBParamsDto> priceHistory = priceRepository.getCatPriceHistory(catId);

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
