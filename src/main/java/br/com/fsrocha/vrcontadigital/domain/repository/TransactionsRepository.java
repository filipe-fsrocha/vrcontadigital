package br.com.fsrocha.vrcontadigital.domain.repository;

import br.com.fsrocha.vrcontadigital.domain.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<TransactionEntity, UUID> {
}
