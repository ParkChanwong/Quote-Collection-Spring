package com.ohgiraffers.quote_collection_spring.service.person;

import com.ohgiraffers.quote_collection_spring.dto.person.PersonRequestDTO;
import com.ohgiraffers.quote_collection_spring.dto.person.PersonResponseDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import com.ohgiraffers.quote_collection_spring.entity.category.FieldEntity;
import com.ohgiraffers.quote_collection_spring.entity.category.PeriodEntity;
import com.ohgiraffers.quote_collection_spring.entity.person.PersonEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.country.EmptyCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.NotFoundCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.field.NotFoundFieldException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.NotFoundPeriodException;
import com.ohgiraffers.quote_collection_spring.exception.person.EmptyPersonException;
import com.ohgiraffers.quote_collection_spring.exception.person.NotFoundPersonException;
import com.ohgiraffers.quote_collection_spring.repository.category.CountryRepository;
import com.ohgiraffers.quote_collection_spring.repository.category.FieldRepository;
import com.ohgiraffers.quote_collection_spring.repository.category.PeriodRepository;
import com.ohgiraffers.quote_collection_spring.repository.person.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;
    private final PeriodRepository periodRepository;
    private final FieldRepository fieldRepository;

    @Autowired
    public PersonService(
            PersonRepository personRepository,
            CountryRepository countryRepository,
            PeriodRepository periodRepository,
            FieldRepository fieldRepository
    ) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.periodRepository = periodRepository;
        this.fieldRepository = fieldRepository;
    }

    private PersonResponseDTO convertToDTO(PersonEntity personEntity) {
        return new PersonResponseDTO(
                personEntity.getId(),
                personEntity.getCountry().getName(),
                personEntity.getPeriod().getName(),
                personEntity.getField().getName(),
                personEntity.getName()
        );
    }

    private CountryEntity findCountryOrThrow(int countryId) {
        return countryRepository.findById(countryId).orElseThrow(NotFoundCountryException::new);
    }

    private PeriodEntity findPeriodOrThrow(int periodId) {
        return periodRepository.findById(periodId).orElseThrow(NotFoundPeriodException::new);
    }

    private FieldEntity findFieldOrThrow(int fieldId) {
        return fieldRepository.findById(fieldId).orElseThrow(NotFoundFieldException::new);
    }

    private PersonEntity convertToEntity(PersonRequestDTO personRequestDTO, CountryEntity countryEntity, PeriodEntity periodEntity, FieldEntity fieldEntity) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setCountry(countryEntity);
        personEntity.setPeriod(periodEntity);
        personEntity.setField(fieldEntity);
        personEntity.setName(personRequestDTO.getName());

        return personEntity;
    }

    // 전체 인물 조회
    public List<PersonResponseDTO> findAllPersons() {
        List<PersonEntity> persons = personRepository.findAll(Sort.by("name", "countryName", "fieldName", "periodName", "id"));

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 국가명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByCountryName(String countryName) {
        List<PersonEntity> persons = personRepository.findByCountryNameContaining(countryName, Sort.by("name", "countryName", "fieldName", "periodName", "id"));

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 시대명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByPeriodName(String periodName) {
        List<PersonEntity> persons = personRepository.findByPeriodNameContaining(periodName, Sort.by("name", "countryName", "fieldName", "periodName", "id"));

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 분야명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByFieldName(String fieldName) {
        List<PersonEntity> persons = personRepository.findByFieldNameContaining(fieldName, Sort.by("name", "countryName", "fieldName", "periodName", "id"));

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 인물명으로 인물 조회
    public List<PersonResponseDTO> findAllPersonsByName(String name) {
        List<PersonEntity> persons = personRepository.findByNameContaining(name, Sort.by("name", "countryName", "fieldName", "periodName", "id"));

        return persons.stream().map(this::convertToDTO).toList();
    }

    // 인물명 ID 단일 조회
    public PersonResponseDTO findPersonById(int id) {
        PersonEntity person = personRepository
                .findById(id)
                .orElseThrow(NotFoundPersonException::new);

        return convertToDTO(person);
    }

    // 인물 등록
    @Transactional
    public void savePerson(PersonRequestDTO personDTO) {
        CountryEntity country = findCountryOrThrow(personDTO.getCountryId());
        PeriodEntity period = findPeriodOrThrow(personDTO.getPeriodId());
        FieldEntity field = findFieldOrThrow(personDTO.getFieldId());

        if (personDTO.getName() == null || personDTO.getName().isBlank()) {
            throw new EmptyPersonException();
        }

        personRepository.save(convertToEntity(personDTO, country, period, field));
    }

    // 인물 수정
    @Transactional
    public void modifyPerson(int id, PersonRequestDTO personDTO) {
        PersonEntity person = personRepository
                .findById(id)
                .orElseThrow(NotFoundPersonException::new);

        CountryEntity country = findCountryOrThrow(personDTO.getCountryId());
        PeriodEntity period = findPeriodOrThrow(personDTO.getPeriodId());
        FieldEntity field = findFieldOrThrow(personDTO.getFieldId());

        if (personDTO.getName() == null || personDTO.getName().isBlank()) {
            throw new EmptyPersonException();
        }

        person.setCountry(country);
        person.setPeriod(period);
        person.setField(field);
        person.setName(personDTO.getName());
    }

    // 인물 삭제
    @Transactional
    public void deletePerson(int id) {
        PersonEntity person = personRepository
                .findById(id)
                .orElseThrow(NotFoundPersonException::new);

        personRepository.delete(person);
    }
}
