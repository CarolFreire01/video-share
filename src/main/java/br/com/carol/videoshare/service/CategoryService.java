package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundException;
import br.com.carol.videoshare.repository.CategoryRepository;
import br.com.carol.videoshare.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final VideoRepository videoRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        validateRequest(categoryDto);

        Category category = this.dtoToEntity(categoryDto);
        Category newCategory = repository.save(category);

        return new CategoryDto(newCategory);
    }

    public Optional<Category> findCategoryById(Long id) {
        return repository.findById(id);
    }

    public List<CategoryDto> findAllCategory(Pageable pageable) {
        Page<Category> allCategories = repository.findAll(pageable);

        if (allCategories.isEmpty()){
            throw new ObjectNotFoundException("Categories not found");
        }

        return allCategories.stream()
                .map(this::buildCategory)
                .collect(Collectors.toList());
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        validateRequest(categoryDto);

        if (repository.findById(id).isPresent()){
            Category existingCategory = repository.findById(id).get();

            existingCategory.setTitle(categoryDto.getTitle());
            existingCategory.setColor(categoryDto.getColor());

            Category updatedCategory = repository.save(existingCategory);

            return new CategoryDto(updatedCategory);
        } else {
            throw new ObjectNotFoundException("Category not found");
        }
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    public List<Video> findVideosByCategoryId(Long id_category, Pageable pageable) {
        Optional<Category> categories = repository.findById(id_category);

        try {
            return videoRepository.findByCategory(categories, pageable);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Id Category not found");
        }
    }

    private void validateRequest(CategoryDto categoryDto){
        if(StringUtils.isBlank(categoryDto.getTitle())){
            throw new BadRequestException("Title is empty");
        } else if(StringUtils.isBlank(categoryDto.getColor())){
            throw new BadRequestException("Color is empty");
        }
    }

    private Category dtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        return category;
    }

    private CategoryDto buildCategory(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .color(category.getColor())
                .build();
    }
}
