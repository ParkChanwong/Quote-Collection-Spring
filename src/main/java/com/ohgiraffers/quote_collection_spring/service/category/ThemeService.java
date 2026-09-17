package com.ohgiraffers.quote_collection_spring.service.category;

import com.ohgiraffers.quote_collection_spring.dto.category.ThemeDTO;
import com.ohgiraffers.quote_collection_spring.entity.category.ThemeEntity;
import com.ohgiraffers.quote_collection_spring.exception.category.theme.NotFoundThemeException;
import com.ohgiraffers.quote_collection_spring.repository.category.ThemeRepository;
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

    public ThemeDTO convertToDTO(ThemeEntity themeEntity){
        return new ThemeDTO(themeEntity.getId(), themeEntity.getName());
    }

    public List<ThemeDTO> findAllThemes() {
        List<ThemeEntity> themes = themeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return themes.stream().map(this::convertToDTO).toList();
    }

    public ThemeDTO findThemeById(int id) {
        ThemeEntity theme = themeRepository
                .findById(id)
                .orElseThrow(NotFoundThemeException::new);

        return convertToDTO(theme);
    }
}
