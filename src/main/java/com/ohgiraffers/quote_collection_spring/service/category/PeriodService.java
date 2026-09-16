package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.PeriodDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.PeriodEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.period.NotFoundPeriodException;
import com.ohgiraffers.quote_collection_spring.repository.category.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {
    private PeriodRepository periodRepository;

    @Autowired
    public PeriodService(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    public PeriodDTO convertToDTO(PeriodEntity periodEntity) {
        return new PeriodDTO(periodEntity.getId(), periodEntity.getName());
    }

    // 전체 시대 조회
    public List<PeriodDTO> findAllPeriods() {
        List<PeriodEntity> periods = periodRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return periods.stream().map(this::convertToDTO).toList();
    }

    // 시대 ID 단일 조회
    public PeriodDTO findPeriodById(int id) {
        PeriodEntity period =  periodRepository
                .findById(id)
                .orElseThrow(NotFoundPeriodException::new);

        return convertToDTO(period);
    }
}
