package br.com.fsrocha.vrcontadigital.domain.repository;

import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    @Query("SELECT ac FROM AccountEntity ac WHERE ac.account = ?1")
    Optional<AccountEntity> findByAccount(String accountNumber);

    @Query("SELECT ac FROM AccountEntity ac JOIN ac.keysPix kp WHERE kp.key = :keyPix AND ac.password = :password")
    Optional<AccountEntity> findByKeyPixAndPassword(@Param("keyPix") String keyPix, @Param("password") String password);

}
