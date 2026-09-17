package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.PeriodDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.PeriodEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.country.DuplicateCountryException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.EmptyPeriodException;
import com.ohgiraffers.quote_collection_spring.exception.category.period.NotFoundPeriodException;
import com.ohgiraffers.quote_collection_spring.repository.category.PeriodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {
    private static final Sort PERIOD_SORT = Sort.by("name");

    private final PeriodRepository periodRepository;

    @Autowired
    public PeriodService(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    private PeriodDTO convertToDTO(PeriodEntity periodEntity) {
        return new PeriodDTO(periodEntity.getId(), periodEntity.getName());
    }

    private PeriodEntity convertToEntity(PeriodDTO periodDTO) {
        PeriodEntity periodEntity = new PeriodEntity();
        periodEntity.setName(periodDTO.getName().trim());

        return periodEntity;
    }

    // 전체 시대 조회
    public List<PeriodDTO> findAllPeriods() {
        List<PeriodEntity> periods = periodRepository.findAll(PERIOD_SORT);

        return periods.stream().map(this::convertToDTO).toList();
    }

    public List<PeriodDTO> findAllPeriodsByName(String name) {
        List<PeriodEntity> periods = periodRepository.findByNameContaining(name, PERIOD_SORT);

        return periods.stream().map(this::convertToDTO).toList();
    }

    // 시대 ID 단일 조회
    public PeriodDTO findPeriodById(int id) {
        PeriodEntity period =  periodRepository
                .findById(id)
                .orElseThrow(NotFoundPeriodException::new);

        return convertToDTO(period);
    }

    // 시대 등록
    @Transactional
    public void savePeriod(PeriodDTO periodDTO) {
        if (periodDTO.getName() == null || periodDTO.getName().isBlank()) {
            throw new EmptyPeriodException();
        } else if (periodRepository.existsByName(periodDTO.getName().trim())) {
            throw new DuplicateCountryException();
        }

        periodRepository.save(convertToEntity(periodDTO));
    }

    // 시대 수정
    @Transactional
    public void modifyPeriod(int id, PeriodDTO periodDTO) {
        PeriodEntity period = periodRepository
                .findById(id)
                .orElseThrow(NotFoundPeriodException::new);
        if (periodDTO.getName() == null || periodDTO.getName().isBlank()) {
            throw new EmptyPeriodException();
        } else if (periodRepository.existsByName(periodDTO.getName().trim())) {
            throw new DuplicateCountryException();
        }

        period.setName(periodDTO.getName().trim());
    }

    // 시대 삭제
    @Transactional
    public void deletePeriod(int id) {
        PeriodEntity period = periodRepository
                .findById(id)
                .orElseThrow(NotFoundPeriodException::new);

        periodRepository.delete(period);
    }
}
