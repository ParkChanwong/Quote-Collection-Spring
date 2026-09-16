package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.FieldDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.FieldEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.Field.DuplicateFieldException;
import com.ohgiraffers.quote_collection_spring.exception.category.Field.EmptyFieldException;
import com.ohgiraffers.quote_collection_spring.exception.category.Field.NotFoundFieldException;
import com.ohgiraffers.quote_collection_spring.repository.category.FieldRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public FieldEntity convertToEntity(FieldDTO fieldDTO) {
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setName(fieldDTO.getName());

        return fieldEntity;
    }

    // 전체 분야 조회
    public List<FieldDTO> findAllFields() {
        List<FieldEntity> fields = fieldRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return fields.stream().map(this::convertToDTO).toList();
    }

    // 분야 ID 단일 조회
    public FieldDTO findFieldById(int id) {
        FieldEntity fieldEntity = fieldRepository
                .findById(id)
                .orElseThrow(NotFoundFieldException::new);

        return convertToDTO(fieldEntity);
    }

    // 분야 등록
    @Transactional
    public void saveField(FieldDTO fieldDTO) {
        if (fieldDTO.getName() == null || fieldDTO.getName().isBlank()) {
            throw new EmptyFieldException();
        } else if (fieldRepository.existsByName(fieldDTO.getName())) {
            throw new DuplicateFieldException();
        }

        fieldRepository.save(convertToEntity(fieldDTO));
    }

    // 분야 수정
    @Transactional
    public void modifyField(int id, FieldDTO fieldDTO) {
        FieldEntity field = fieldRepository
                .findById(id)
                .orElseThrow(NotFoundFieldException::new);

        if (fieldDTO.getName() == null || fieldDTO.getName().isBlank()) {
            throw new EmptyFieldException();
        } else if (fieldRepository.existsByName(fieldDTO.getName())) {
            throw new DuplicateFieldException();
        }

        field.setName(fieldDTO.getName());
    }
}
