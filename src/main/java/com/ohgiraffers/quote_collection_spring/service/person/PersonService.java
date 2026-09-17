package com.ohgiraffers.quote_collection_spring.service.person;

import com.ohgiraffers.quote_collection_spring.dto.person.PersonResponseDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import com.ohgiraffers.quote_collection_spring.entity.person.PersonEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.period.NotFoundPeriodException;
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

    // 전체 인물 조회
    public List<PersonResponseDTO> findAllPersons() {
        List<PersonEntity> persons = personRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 국가명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByCountryName(String countryName) {
        List<PersonEntity> persons = personRepository.findByCountryNameContaining(countryName);

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 시대명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByPeriodName(String periodName) {
        List<PersonEntity> persons = personRepository.findByPeriodNameContaining(periodName);

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 분야명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByFieldName(String fieldName) {
        List<PersonEntity> persons = personRepository.findByFieldNameContaining(fieldName);

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 인물명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByName(String name) {
        List<PersonEntity> persons = personRepository.findByNameContaining(name);

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 인물명 ID 단일 조회
    public PersonResponseDTO findPersonById(int id) {
        PersonEntity person = personRepository
                .findById(id)
                .orElseThrow(NotFoundPeriodException::new);

        return convertToDTO(person);
    }
}
