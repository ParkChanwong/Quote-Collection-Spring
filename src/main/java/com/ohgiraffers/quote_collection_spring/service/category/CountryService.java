package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.CountryDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.country.DuplicateCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.EmptyCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.country.NotFoundCountryException;
import com.ohgiraffers.quote_collection_spring.repository.category.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        countryEntity.setName(countryDTO.getName());

        return countryEntity;
    }

    public List<CountryDTO> findAllCountries() {
        List<CountryEntity> countries = countryRepository.findAll();

        return countries.stream().map(this::convertToDTO).toList();
    }

    public CountryDTO findCountryById(int countryId){
        CountryEntity countryEntity = countryRepository
                .findById(countryId)
                .orElseThrow(NotFoundCountryException::new);

        return convertToDTO(countryEntity);
    }

    @Transactional
    public void saveCountry(CountryDTO countryDTO){
        if (countryDTO.getName() == null || countryDTO.getName().isBlank()){
            throw new EmptyCountryException();
        } else if (countryRepository.existsByName(countryDTO.getName())){
            throw new DuplicateCountryException();
        }

        countryRepository.save(convertToEntity(countryDTO));
    }
}
