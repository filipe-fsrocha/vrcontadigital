package br.com.fsrocha.vrcontadigital.domain.repository;

import br.com.fsrocha.vrcontadigital.domain.model.NextAccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface NextAccountRepository extends JpaRepository<NextAccountEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT nta FROM NextAccountEntity nta WHERE nta.id = ?1")
    Optional<NextAccountEntity> getNextAccount(UUID id);

}
