package ru.sds.straycats.utils;

import lombok.experimental.UtilityClass;
import ru.sds.straycats.exception.BadRequestException;
import ru.sds.straycats.model.dto.CatPriceDBParamsDto;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@UtilityClass
public class StrayCatsUtils {

    Random rand = new Random();

    public String generateName() {
        List<String> firstName = Arrays.asList("Fluffy", "Dummy", "Orange", "Sparkling", "Extra large");
        List<String> lastName = Arrays.asList("Pumpkin", "Disaster", "Monsieur", "Weirdo", "Shine");

        StringBuilder sb = new StringBuilder();
        sb.append(firstName.get(rand.nextInt(firstName.size())));
        sb.append(" ");
        sb.append(lastName.get(rand.nextInt(lastName.size())));

        return sb.toString();
    }

    public void validateGender(Integer gender) {
        List<Integer> genders = Arrays.asList(0, 1);

        if (!genders.contains(gender)) {
            throw new BadRequestException(String.format("Invalid gender %s. Cat gender should be binary: 0 - Female, 1 - Male", gender));
        }
    }

    public void validatePrice(Double price) {
        if (price < 0) {
            throw new BadRequestException("Price should be non-negative number");
        }
    }

    public CatPriceDBParamsDto getRandomCat(List<CatPriceDBParamsDto> catList) {
        return catList.get(rand.nextInt(catList.size()));
    }
}
