package com.ohgiraffers.quote_collection_spring.service.person;

import com.ohgiraffers.quote_collection_spring.dto.person.PersonResponseDTO;
import com.ohgiraffers.quote_collection_spring.entity.person.PersonEntity;
import com.ohgiraffers.quote_collection_spring.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonResponseDTO convertToDTO(PersonEntity personEntity) {
        return new PersonResponseDTO(
                personEntity.getId(),
                personEntity.getCountry().getName(),
                personEntity.getPeriod().getName(),
                personEntity.getField().getName(),
                personEntity.getName()
        );
    }

    public List<PersonResponseDTO> findAllPersons() {
        List<PersonEntity> persons = personRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return persons.stream().map(this::convertToDTO).toList();
    }
}
