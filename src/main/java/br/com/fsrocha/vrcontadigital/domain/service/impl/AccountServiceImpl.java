package br.com.fsrocha.vrcontadigital.domain.service.impl;

import br.com.fsrocha.vrcontadigital.application.exception.AccountExistsException;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.AccountRepository;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import br.com.fsrocha.vrcontadigital.domain.service.PersonService;
import br.com.fsrocha.vrcontadigital.domain.utils.NextAccountNumber;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

    PersonService personService;
    AccountRepository accountRepository;

    NextAccountNumber nextAccountNumber;

    @Override
    @Transactional
    public AccountEntity createAccount(AccountEntity entity) {
        checkExistsAccount(entity.getPerson().getCpf());
        entity.setAccount(nextAccountNumber.get());
        return accountRepository.save(entity);
    }

    private void checkExistsAccount(String cpf) {
        personService.findByCpf(cpf)
                .ifPresent(value -> {
                    LOGGER.warn("There is already an account with this CPF: {}", cpf);
                    throw new AccountExistsException();
                });
    }
}
