package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.FieldDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.FieldEntity;
import com.ohgiraffers.quote_collection_spring.repository.category.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public FieldDTO convertToDTO(FieldEntity fieldEntity) {
        return new FieldDTO(fieldEntity.getId(), fieldEntity.getName());
    }

    // 전체 분야 조회
    public List<FieldDTO> findAllFields() {
        List<FieldEntity> fields = fieldRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return fields.stream().map(this::convertToDTO).toList();
    }
}
