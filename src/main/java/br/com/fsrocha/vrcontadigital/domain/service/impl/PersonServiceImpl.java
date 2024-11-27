package br.com.fsrocha.vrcontadigital.domain.service.impl;

import br.com.fsrocha.vrcontadigital.domain.model.PersonEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.PersonRepository;
import br.com.fsrocha.vrcontadigital.domain.service.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonServiceImpl implements PersonService {

    PersonRepository personRepository;

    @Override
    public Optional<PersonEntity> findByCpf(String cpf) {
        return personRepository.findByCpf(cpf);
    }
}
