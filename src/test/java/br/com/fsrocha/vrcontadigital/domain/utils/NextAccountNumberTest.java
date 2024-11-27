package br.com.fsrocha.vrcontadigital.domain.utils;

import br.com.fsrocha.vrcontadigital.domain.model.NextAccountEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.NextAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

class NextAccountNumberTest {

    private static final UUID NEXT_ACCOUNT_ID = UUID.fromString("0edf91b8-d7c1-4e0f-8053-83fdd8947bd9");

    @Mock
    private NextAccountRepository nextAccountRepository;

    @InjectMocks
    private NextAccountNumber nextAccountNumber;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get next account number")
    void testGetNextAccountNumber() {
        // Assemble
        var nextAccountEntity = new NextAccountEntity();
        nextAccountEntity.setNextAccountNumber("00001");

        Mockito.when(nextAccountRepository.getNextAccount(NEXT_ACCOUNT_ID))
                .thenReturn(Optional.of(nextAccountEntity));

        // Act
        var result = nextAccountNumber.get();

        // Assert
        Assertions.assertEquals("00001", result);

        // Verify
        Mockito.verify(nextAccountRepository).getNextAccount(NEXT_ACCOUNT_ID);
        Mockito.verify(nextAccountRepository).save(nextAccountEntity);

        Assertions.assertEquals("00002", nextAccountEntity.getNextAccountNumber());
        Assertions.assertEquals(LocalDateTime.now().getDayOfMonth(), nextAccountEntity.getLastUpdated().getDayOfMonth());
    }
}
