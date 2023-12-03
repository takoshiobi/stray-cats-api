package ru.sds.straycats.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
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
}
