package br.com.fsrocha.vrcontadigital.domain.model;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    AccountEntity account;

    @Column(name = "amount", nullable = false)
    BigDecimal amount;

    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @Column(name = "created_at", nullable = false)
    LocalDateTime localDateTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    TransactionStatus status;

    public TransactionEntity() {
    }

    public TransactionEntity(AccountEntity account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }
}
