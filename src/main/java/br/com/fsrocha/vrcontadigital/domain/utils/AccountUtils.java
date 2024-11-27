package br.com.fsrocha.vrcontadigital.domain.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class AccountUtils {

    private static final String BANK_NUMBER = "610";
    private static final int MAX_LENGTH_CARD_NUMBER = 16;
    private static final int MAX_LENGTH_PASSWORD = 4;

    public static String getBankNumber() {
        return BANK_NUMBER;
    }

    public static String generateCardNumber() {
        return generateRandomNumbers(MAX_LENGTH_CARD_NUMBER);
    }

    public static String generatePassword() {
        return generateRandomNumbers(MAX_LENGTH_PASSWORD);
    }

    private static String generateRandomNumbers(int length) {
        var secureRandom = new SecureRandom();
        var randomNumber = new StringBuilder();

        for (int count = 0; count < length; count++) {
            randomNumber.append(secureRandom.nextInt(10));
        }

        return randomNumber.toString();
    }
}
