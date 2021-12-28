package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    static Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        String[] cardNumber = {"4444 4444 4444 444", "2"};
        int random = new Random().nextInt(cardNumber.length);
        return cardNumber[random];
    }

    public static String getEmptyCardNumber() {
        return " ";
    }

    public static String getValidMonthNumber() {
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int random = new Random().nextInt(month.length);
        return month[random];
    }

    public static String getInvalidMonthNumber() {
        String[] month = {"20", "33", "44", "55", "1", "3", "5", "00"};
        int random = new Random().nextInt(month.length);
        return month[random];
    }

    public static String getEmptyMonthNumber() {
        return " ";
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(3).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidLastYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYear() {
        String[] year = {"1", "3", "5", "9", "7", "00"};
        int random = new Random().nextInt(year.length);
        return year[random];
    }

    public static String getEmptyYear() {
        return " ";
    }

    public static String getValidOwner() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getInvalidOwnerWithNumber() {
        String[] number = {"562376", "68746276", "985367233", "96273561"};
        int random = new Random().nextInt(number.length);
        return number[random];
    }

    public static String getInvalidOwnerWithCir() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getEmptyOwner() {
        return " ";
    }

    public static String getValidCvc() {
        String cvc = faker.numerify("###");
        return cvc;
    }

    public static String getInvalidCvc() {
        String cvc = faker.numerify("#");
        return cvc;
    }

    public static String getEmptyCvc() {
        return " ";
    }
}
