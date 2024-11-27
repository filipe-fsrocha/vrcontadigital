package br.com.fsrocha.vrcontadigital.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "account")
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    UUID id;

    @OneToOne(mappedBy = "account", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    PersonEntity person;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    BalanceEntity balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.PERSIST)
    List<KeysPixEntity> keysPix;

    @Column(name = "bank")
    String bank;

    @Column(name = "account")
    String account;

    @Column(name = "card_number")
    String cardNumber;

    @Column(name = "password")
    String password;

}
