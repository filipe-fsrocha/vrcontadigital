package br.com.fsrocha.vrcontadigital.application.mapper;

import br.com.fsrocha.vrcontadigital.application.dto.request.AccountRequest;
import br.com.fsrocha.vrcontadigital.application.dto.response.AccountResponse;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.KeysPixEntity;
import br.com.fsrocha.vrcontadigital.domain.model.PersonEntity;
import br.com.fsrocha.vrcontadigital.domain.utils.AccountUtils;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class AccountMapper {

    public static AccountEntity toEntity(AccountRequest request) {
        var account = new AccountEntity();
        account.setPerson(toPerson(account, request));
        account.setBalance(toBalance(account));
        account.setKeysPix(toKeyPix(account, request.getCpf()));
        account.setBank(AccountUtils.getBankNumber());
        account.setCardNumber(AccountUtils.generateCardNumber());
        account.setPassword(AccountUtils.generatePassword());
        return account;
    }

    public static AccountResponse toResponse(AccountEntity entity) {
        var response = new AccountResponse();
        response.setBanco(entity.getBank());
        response.setConta(entity.getAccount());
        response.setCartao(entity.getCardNumber());
        response.setSenha(entity.getPassword());
        return response;
    }

    private static PersonEntity toPerson(AccountEntity account, AccountRequest request) {
        var person = new PersonEntity(request.getCpf(), request.getNome());
        person.setAccount(account);
        return person;
    }

    private static BalanceEntity toBalance(AccountEntity account) {
        return new BalanceEntity(account, BigDecimal.ZERO, LocalDateTime.now());
    }

    private static List<KeysPixEntity> toKeyPix(AccountEntity account, String cpf) {
        var pix = new KeysPixEntity(cpf);
        pix.setAccount(account);
        pix.setCreatedAt(LocalDateTime.now());
        return List.of(pix);
    }
}
