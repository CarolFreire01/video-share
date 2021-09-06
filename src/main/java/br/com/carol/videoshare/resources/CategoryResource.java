package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.CategoryDto;
import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> createdCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCategory(categoryDto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAllCategory(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        Pageable pageReq = pageable(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllCategory(pageReq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCategoryById(id));
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<?> findVideosByCategoryId(@PathVariable Long id, @RequestParam int offset, @RequestParam int limit){
        Pageable pageReq = pageable(offset, limit);
        List<Video> videoDto = service.findVideosByCategoryId(id, pageReq);
        return ResponseEntity.status(HttpStatus.OK).body(videoDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateCategory(categoryDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    private Pageable pageable(Integer offset, Integer limit){
        int page = offset != null ? offset : 0;
        int size = limit != null ? limit : 5;

        return PageRequest.of(page, size);
    }
}

