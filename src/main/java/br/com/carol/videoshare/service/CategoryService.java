package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.CategoryRequest;
import br.com.carol.videoshare.dto.CategoryResponse;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundException;
import br.com.carol.videoshare.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        validateRequest(categoryRequest);

        Category category = this.buildCategoryRequest(categoryRequest);
        Category newCategory = categoryRepository.save(category);

        return buildCategoryResponse(newCategory);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findCategoryById(id);
    }

    public List<CategoryResponse> findAllCategory(Pageable pageable) {
        Page<Category> findAllCategories = categoryRepository.findAll(pageable);

        if (findAllCategories.isEmpty()){
            throw new ObjectNotFoundException("Categories not found");
        }

        return findAllCategories.stream()
                .map(this::buildCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long idCategory) {
        Category existingCategory = categoryRepository.findCategoryById(idCategory);

        existingCategory.updateCategory(categoryRequest);

        Category updatedCategory = categoryRepository.save(existingCategory);

        return buildCategoryResponse(updatedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private void validateRequest(CategoryRequest categoryRequest){
        if (StringUtils.isBlank(categoryRequest.getTitle())){
            throw new BadRequestException("Title is empty");
        } else if(StringUtils.isBlank(categoryRequest.getColor())){
            throw new BadRequestException("Color is empty");
        }
    }

    public Category buildCategoryRequest(CategoryRequest requestDto) {
        return Category.builder()
                .id(requestDto.getId())
                .title(requestDto.getTitle())
                .color(requestDto.getColor())
                .build();
    }

    public CategoryResponse buildCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .color(category.getColor())
                .build();
    }
}
