package br.com.fsrocha.vrcontadigital.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@Table(name = "person")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    UUID id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    @ToString.Exclude
    AccountEntity account;

    @Column(name = "cpf")
    String cpf;

    @Column(name = "name")
    String name;

    public PersonEntity() {
    }

    public PersonEntity(String cpf, String name) {
        this.cpf = cpf;
        this.name = name;
    }
}
