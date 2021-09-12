package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.VideoRequest;
import br.com.carol.videoshare.dto.VideoResponse;
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
@RequestMapping("/videos")
public class VideoResource {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoResponse> createVideo(@RequestBody VideoRequest videoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.addVideo(videoRequest));
    }

    @GetMapping
    public ResponseEntity<List<VideoResponse>> findAllVideos(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        Pageable pageReq = pageable(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(videoService.findAllVideos(pageReq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> findVideo(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.findVideoById(id));
    }

    @GetMapping("/title={title}")
    public ResponseEntity<List<VideoResponse>> findVideoByName(@PathVariable("title") String title, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        Pageable pageReq = pageable(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(videoService.findVideoByTitle(title, pageReq));
    }

    @GetMapping("/free")
    public ResponseEntity<List<VideoResponse>> findVideosFree() {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.listFreeVideos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable("id") Long id, @RequestBody VideoRequest responseDto) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.updateVideo(responseDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateVideo(@PathVariable("id") Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    private Pageable pageable(Integer offset, Integer limit){
        int page = offset != null ? offset : 0;
        int size = limit != null ? limit : 5;

        return PageRequest.of(page, size);
    }
}
