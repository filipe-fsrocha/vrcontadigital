package br.com.fsrocha.vrcontadigital.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "next_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NextAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    UUID id;

    @Column(name = "next_account_number", nullable = false)
    String nextAccountNumber;

    @Column(name = "last_updated")
    LocalDateTime lastUpdated;
}
