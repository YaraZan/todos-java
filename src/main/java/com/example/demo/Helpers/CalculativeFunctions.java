package com.example.demo.Helpers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculativeFunctions {
    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int ID_LENGTH = 10;

    private static String DATE_TO_RETURN;

    public static String generateId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static String getNeedableDate(String keyword) {
        LocalDate currentDate = LocalDate.now();

        if (keyword == "week") {
            LocalDate nextWeek = currentDate.plusWeeks(1);
            DATE_TO_RETURN = nextWeek.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } else if (keyword == "year") {
            LocalDate nextYear = currentDate.plusYears(1);
            DATE_TO_RETURN = nextYear.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }

        return DATE_TO_RETURN;
    }

    public static List<Double> getRandomList(int size) {
        List<Double> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            list.add(random.nextDouble() * 100);
        }

        return list;
    }
}
