package br.com.fsrocha.vrcontadigital.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "key_pix")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeysPixEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    UUID id;

    @Column(name = "_key", nullable = false)
    String key;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    AccountEntity account;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    public KeysPixEntity(String key) {
        this.key = key;
    }
}
