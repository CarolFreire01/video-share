package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundExpection;
import br.com.carol.videoshare.repository.CategoryRepository;
import br.com.carol.videoshare.repository.VideoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Autowired
    VideoRepository videoRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        validateRequest(categoryDto);

        Category category = this.dtoToEntity(categoryDto);
        Category newCategory = repository.save(category);

        return new CategoryDto(newCategory);
    }

    public Optional<Category> findCategoryById(Long id) {
        return repository.findById(id);
    }

    public Page<Category> findAllCategory(int offset, int limit) {
        Pageable paging = PageRequest.of(offset, limit);

        return repository.findAll(paging);
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
            throw new ObjectNotFoundExpection("Category not found");
        }
    }

    public void deleteCategory(Long id) {
        repository.deleteById(id);
    }

    public Page<Video> findVideosByCategoryId(Long id_category, int offset, int limit) {
        Optional<Category> categories = repository.findById(id_category);

        if (Objects.nonNull(categories)){
            Page<Video> result = videoRepository.findByCategory(categories, PageRequest.of(offset, limit));
            return result;
        } else {
            throw new ObjectNotFoundExpection("Id Category not found");
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
}
