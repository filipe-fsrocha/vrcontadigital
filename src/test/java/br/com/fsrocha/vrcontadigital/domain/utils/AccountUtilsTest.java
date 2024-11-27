package br.com.fsrocha.vrcontadigital.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountUtilsTest {

    @Test
    @DisplayName("Obtain bank number")
    void testGetBankNumber() {
        Assertions.assertEquals("610", AccountUtils.getBankNumber());
    }

    @Test
    @DisplayName("Generate card number")
    void testGeneratedCardNumber() {
        var cardNumber = AccountUtils.generateCardNumber();
        Assertions.assertNotNull(cardNumber);
        Assertions.assertEquals(16, cardNumber.length());
    }

    @Test
    @DisplayName("Generate password")
    void testGeneratePassword() {
        var password = AccountUtils.generatePassword();
        Assertions.assertNotNull(password);
        Assertions.assertEquals(4, password.length());
    }
}