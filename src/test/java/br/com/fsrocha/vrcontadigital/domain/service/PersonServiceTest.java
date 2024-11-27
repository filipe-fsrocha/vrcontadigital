package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.model.PersonEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.PersonRepository;
import br.com.fsrocha.vrcontadigital.domain.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test find by cpf when person exists")
    void testFindByCpfWhenPersonExists() {
        // Assemble
        String cpf = "11111111111";
        var person = new PersonEntity();
        person.setCpf(cpf);
        person.setName("VR Benefícios");

        Mockito.when(personRepository.findByCpf(cpf)).thenReturn(Optional.of(person));

        // Act
        Optional<PersonEntity> result = personService.findByCpf(cpf);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("VR Benefícios", result.get().getName());
        Assertions.assertEquals(cpf, result.get().getCpf());

        Mockito.verify(personRepository).findByCpf(cpf);
    }

    @Test
    @DisplayName("Find by cpf person dows not exists")
    void testFindByCpfWhenPersonDoesNotExists() {
        // Assemble
        String cpf = "11111111111";
        Mockito.when(personRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        // Act
        Optional<PersonEntity> result = personService.findByCpf(cpf);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(personRepository).findByCpf(cpf);
    }
}
