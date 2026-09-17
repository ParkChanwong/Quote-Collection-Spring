package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.CountryDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.country.DuplicateCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.EmptyCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.NotFoundCountryException;
import com.ohgiraffers.quote_collection_spring.repository.category.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryDTO convertToDTO(CountryEntity countryEntity){
        return new CountryDTO(countryEntity.getId(), countryEntity.getName());
    }

    public CountryEntity convertToEntity(CountryDTO countryDTO){
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(countryDTO.getName().trim());

        return countryEntity;
    }

    // 전체 국가 조회
    public List<CountryDTO> findAllCountries() {
        // Sort.by로 국가명 가나다 순으로 정렬
        List<CountryEntity> countries = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return countries.stream().map(this::convertToDTO).toList();
    }

    // 국가명 조회
    public List<CountryDTO> findAllCountriesByName(String name) {
        List<CountryEntity> countries = countryRepository.findByNameContaining(name);

        return countries.stream().map(this::convertToDTO).toList();
    }

    // 국가 id 단일 조회
    public CountryDTO findCountryById(int id){
        CountryEntity countryEntity = countryRepository
                .findById(id)
                .orElseThrow(NotFoundCountryException::new);

        return convertToDTO(countryEntity);
    }

    // 국가 등록
    @Transactional
    public void saveCountry(CountryDTO countryDTO){
        if (countryDTO.getName() == null || countryDTO.getName().isBlank()){
            throw new EmptyCountryException();
        } else if (countryRepository.existsByName(countryDTO.getName().trim())){
            throw new DuplicateCountryException();
        }

        countryRepository.save(convertToEntity(countryDTO));
    }

    // 국가 수정
    @Transactional
    public void modifyCountry(int id, CountryDTO countryDTO){
        CountryEntity findCountry = countryRepository
                .findById(id)
                .orElseThrow(NotFoundCountryException::new);

        if (countryDTO.getName() == null || countryDTO.getName().isBlank()){
            throw new EmptyCountryException();
        } else if (countryRepository.existsByName(countryDTO.getName().trim())){
            throw new DuplicateCountryException();
        }

        findCountry.setName(countryDTO.getName().trim());
    }

    // 국가 삭제
    @Transactional
    public void deleteCountry(int id){
        CountryEntity deleteCountry = countryRepository.findById(id).orElseThrow(NotFoundCountryException::new);

        countryRepository.delete(deleteCountry);
    }
}
