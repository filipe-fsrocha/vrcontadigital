package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.application.dto.request.AccountRequest;
import br.com.fsrocha.vrcontadigital.application.exception.AccountExistsException;
import br.com.fsrocha.vrcontadigital.application.exception.NotFoundException;
import br.com.fsrocha.vrcontadigital.application.mapper.AccountMapper;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.PersonEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.AccountRepository;
import br.com.fsrocha.vrcontadigital.domain.service.impl.AccountServiceImpl;
import br.com.fsrocha.vrcontadigital.domain.utils.NextAccountNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

class AccountServiceTest {

    @Mock
    PersonService personService;

    @Mock
    NextAccountNumber nextAccountNumber;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create account success")
    void testCreateAccountSuccess() {
        // Assemble
        var request = new AccountRequest();
        request.setCpf("11111111111");
        request.setNome("VR Benefícios");
        var entity = AccountMapper.toEntity(request);

        Mockito.when(personService.findByCpf(request.getCpf())).thenReturn(Optional.empty());
        Mockito.when(nextAccountNumber.get()).thenReturn("00001");
        Mockito.when(accountRepository.save(entity)).thenReturn(entity);

        // Act
        var result = accountService.createAccount(entity);

        // Assert
        Assertions.assertEquals("610", result.getBank());
        Assertions.assertEquals("00001", result.getAccount());
        Assertions.assertNotNull(result.getCardNumber());
        Assertions.assertNotNull(result.getPassword());

        Mockito.verify(personService).findByCpf(request.getCpf());
        Mockito.verify(nextAccountNumber).get();
        Mockito.verify(accountRepository).save(Mockito.any(AccountEntity.class));
    }

    @Test
    @DisplayName("Try to create an account with an existing CPF")
    void testCreateAccountThrowAccountExistException() {
        // Assemble
        String cpf = "11111111111";
        var person = new PersonEntity();
        person.setCpf(cpf);

        var account = new AccountEntity();
        account.setPerson(person);

        Mockito.when(personService.findByCpf(cpf)).thenReturn(Optional.of(person));

        // Act
        Assertions.assertThrows(AccountExistsException.class, () -> accountService.createAccount(account));

        // Verify
        Mockito.verifyNoInteractions(nextAccountNumber, accountRepository);
    }

    @Test
    @DisplayName("Check balance of account")
    void testCheckBalance() {
        // Assemble
        var account = new AccountEntity();
        account.setBalance(createBalance());

        Mockito.when(accountRepository.findByAccount("00001")).thenReturn(Optional.of(account));

        // Act
        var result = accountService.checkBalance("00001");

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(495.15), result);
    }

    @Test
    @DisplayName("Check balance with account not found")
    void testCheckBalanceWithAccountNotFound() {
        // Assemble
        var account = new AccountEntity();
        account.setBalance(createBalance());

        Mockito.when(accountRepository.findByAccount("00001")).thenReturn(Optional.empty());

        // Act
        Assertions.assertThrows(NotFoundException.class, () -> accountService.checkBalance("00001"));

        // Verify
        Mockito.verify(accountRepository).findByAccount("00001");
    }

    private BalanceEntity createBalance() {
        var balance = new BalanceEntity();
        balance.setBalance(BigDecimal.valueOf(495.15));
        return balance;
    }
}
