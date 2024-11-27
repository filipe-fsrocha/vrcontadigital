package br.com.fsrocha.vrcontadigital.domain.repository;

import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}
