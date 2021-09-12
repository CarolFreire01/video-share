package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.CategoryRequest;
import br.com.carol.videoshare.dto.CategoryResponse;
import br.com.carol.videoshare.dto.VideoResponse;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.service.CategoryService;
import br.com.carol.videoshare.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryService service;
    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createdCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCategory(categoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategory(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        Pageable pageReq = pageable(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllCategory(pageReq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCategoryById(id));
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<List<VideoResponse>> findVideosByCategoryId(@PathVariable Long id, @RequestParam int offset, @RequestParam int limit){
        Pageable pageReq = pageable(offset, limit);
        List<VideoResponse> videoDto = videoService.findVideosByCategoryId(id, pageReq);
        return ResponseEntity.status(HttpStatus.OK).body(videoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateCategory(categoryRequest, id));
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

