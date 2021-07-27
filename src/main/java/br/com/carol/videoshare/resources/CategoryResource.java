package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.service.impl.CategoryServiceImpl;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.openmbean.OpenDataException;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CategoryResource {

    @Autowired
    CategoryServiceImpl service;

    @PostMapping(path = "/categories")
    public ResponseEntity<CategoryDto> createdCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto category = service.addCategory(categoryDto);
        return Objects.nonNull(category) ? ResponseEntity.status(HttpStatus.CREATED).body(category) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping(path = "/categories/{id}")
    public ResponseEntity<Optional<Category>> findCategoryById(@PathVariable("id") Long id){
        Optional<Category> category = service.findCategoryById(id);
        return Objects.nonNull(category) ? ResponseEntity.status(HttpStatus.OK).body(category) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<Category>> findCategoryById(){
        List<Category> category = service.findAllCategory();
        return Objects.nonNull(category) ? ResponseEntity.status(HttpStatus.OK).body(category) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto categoryDto){
        CategoryDto updateCategory = service.updateCategory(categoryDto, id);
        return Objects.nonNull(updateCategory) ? ResponseEntity.status(HttpStatus.OK).body(updateCategory) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id){
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

