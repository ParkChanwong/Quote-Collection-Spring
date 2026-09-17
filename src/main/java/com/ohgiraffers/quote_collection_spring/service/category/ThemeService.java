package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.ThemeDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.ThemeEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.DuplicateThemeException;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.EmptyThemeException;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.NotFoundThemeException;
import com.ohgiraffers.quote_collection_spring.repository.category.ThemeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public ThemeDTO convertToDTO(ThemeEntity themeEntity) {
        return new ThemeDTO(themeEntity.getId(), themeEntity.getName());
    }

    public ThemeEntity convertToEntity(ThemeDTO themeDTO) {
        ThemeEntity themeEntity = new ThemeEntity();
        themeEntity.setName(themeDTO.getName().trim());

        return themeEntity;
    }

    // 전체 주제 조회
    public List<ThemeDTO> findAllThemes() {
        List<ThemeEntity> themes = themeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return themes.stream().map(this::convertToDTO).toList();
    }

    // 주제 ID 단일 조회
    public ThemeDTO findThemeById(int id) {
        ThemeEntity theme = themeRepository
                .findById(id)
                .orElseThrow(NotFoundThemeException::new);

        return convertToDTO(theme);
    }

    // 주제 등록
    @Transactional
    public void saveTheme(ThemeDTO themeDTO) {
        if (themeDTO.getName() == null || themeDTO.getName().isBlank()) {
            throw new EmptyThemeException();
        } else if (themeRepository.existsByName(themeDTO.getName().trim())) {
            throw new DuplicateThemeException();
        }

        themeRepository.save(convertToEntity(themeDTO));
    }

    // 주제 수정
    @Transactional
    public void modifyTheme(int id, ThemeDTO themeDTO) {
        ThemeEntity theme = themeRepository
                .findById(id)
                .orElseThrow(NotFoundThemeException::new);

        if (themeDTO.getName() == null || themeDTO.getName().isBlank()) {
            throw new EmptyThemeException();
        } else if (themeRepository.existsByName(themeDTO.getName())) {
            throw new DuplicateThemeException();
        }

        theme.setName(themeDTO.getName().trim());
    }

    // 주제 삭제
    @Transactional
    public void deleteTheme(int id) {
        ThemeEntity theme = themeRepository
                .findById(id)
                .orElseThrow(NotFoundThemeException::new);

        themeRepository.delete(theme);
    }
}
