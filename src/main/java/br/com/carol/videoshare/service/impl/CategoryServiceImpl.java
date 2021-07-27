package br.com.carol.videoshare.service.impl;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundExpection;
import br.com.carol.videoshare.repository.CategoryRepository;
import br.com.carol.videoshare.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repository;

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        validateRequest(categoryDto);

        Category category = this.dtoToEntity(categoryDto);
        Category newCategory = repository.save(category);

        return new CategoryDto(newCategory);
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> findAllCategory() {
        return repository.findAll();
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        validateRequest(categoryDto);

        if (repository.findById(id).isPresent()){
            Category existingCategory = repository.findById(id).get();

            existingCategory.setTitle(categoryDto.getTitle());
            existingCategory.setColor(categoryDto.getColor());

            Category updatedCategory = repository.save(existingCategory);

            return new CategoryDto(updatedCategory);
        } else {
            throw new ObjectNotFoundExpection("Category not found");
        }
    }

    @Override
    public void deleteCategory(Long id) {
        repository.deleteById(id);
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
}
