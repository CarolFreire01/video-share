package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    Optional<Category> findCategoryById(Long id);

    List<Category> findAllCategory();

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);

    void deleteCategory(Long id);

    List<Video> findVideosByCategoryId(Long id_category);

}
