package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.CountryDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.CountryEntity;
import com.ohgiraffers.quote_collection_spring.repository.category.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryDTO ConvertToDTO(CountryEntity countryEntity){
        return new CountryDTO(countryEntity.getId(), countryEntity.getName());
    }

    public List<CountryDTO> findAllCountries() {
        List<CountryEntity> countries = countryRepository.findAll();

        return countries.stream().map(this::ConvertToDTO).toList();
    }

    public CountryDTO findCountryById(int countryId){
        CountryEntity countryEntity = countryRepository.findById(countryId).orElse(null);

        return ConvertToDTO(countryEntity);
    }
}
