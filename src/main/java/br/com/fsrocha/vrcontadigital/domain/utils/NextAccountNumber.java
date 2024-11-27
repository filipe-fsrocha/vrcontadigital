package br.com.fsrocha.vrcontadigital.domain.utils;

import br.com.fsrocha.vrcontadigital.domain.repository.NextAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NextAccountNumber {

    private static final UUID NEXT_ACCOUNT_ID = UUID.fromString("0edf91b8-d7c1-4e0f-8053-83fdd8947bd9");

    NextAccountRepository nextAccountRepository;

    public String get() {
        AtomicReference<String> accountNumber = new AtomicReference<>();
        nextAccountRepository.getNextAccount(NEXT_ACCOUNT_ID)
                .ifPresent(nextAccount -> {
                    accountNumber.set(nextAccount.getNextAccountNumber());
                    nextAccount.setNextAccountNumber(nextAccountNumber(accountNumber.get()));
                    nextAccount.setLastUpdated(LocalDateTime.now());
                    nextAccountRepository.save(nextAccount);
                });

        return accountNumber.get();
    }

    public String nextAccountNumber(String number) {
        int next = Integer.parseInt(number);
        next += 1;
        return String.format("%05d", next);
    }

}
