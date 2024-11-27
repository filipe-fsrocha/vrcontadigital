package br.com.fsrocha.vrcontadigital.domain.model;

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
@Table(name = "balance")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    UUID id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @ToString.Exclude
    AccountEntity account;

    @Column(name = "balance")
    BigDecimal balance;

    @Column(name = "last_updated")
    LocalDateTime lastUpdate;

    public BalanceEntity() {
    }

    public BalanceEntity(AccountEntity account, BigDecimal balance, LocalDateTime lastUpdate) {
        this.account = account;
        this.balance = balance;
        this.lastUpdate = lastUpdate;
    }
}
