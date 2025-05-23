package utils;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.UUID;

public class TestDataUtils {

    private static final Faker faker = new Faker(new Locale("en-IN"));

    // Basic data generators
    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static String getCustomEmail(String prefix) {
        return prefix + "_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static String getGender() {
        // Optional: Randomize if needed
        return faker.options().option("male", "female");
    }

    public static String getStatus() {
        return faker.options().option("active", "inactive");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    // Optional address/phone if needed in future
    public static String getAddress() {
        return faker.address().fullAddress();
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }
}

