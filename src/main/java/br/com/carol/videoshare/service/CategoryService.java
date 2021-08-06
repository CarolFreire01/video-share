package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    Optional<Category> findCategoryById(Long id);

    Page<Category> findAllCategory(int offset, int limit);

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    void deleteCategory(Long id);

    Page<Video> findVideosByCategoryId(Long id_category, int offset, int limit);

}
