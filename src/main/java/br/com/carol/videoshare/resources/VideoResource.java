package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.service.VideoService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/videos")
public class VideoResource {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.addVideo(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<VideoDto>> findAllVideos(@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        Pageable pageReq = pageable(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(videoService.findAllVideos(pageReq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Video>> findVideo(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.findVideoById(id));
    }

    @GetMapping("/title={title}")
    public ResponseEntity<List<VideoDto>> findVideoByName(@PathVariable("title") String title, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
        Pageable pageReq = pageable(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(videoService.findVideoByTitle(title, pageReq));
    }

    @GetMapping("/free")
    public ResponseEntity<List<Video>> findVideosFree() {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.listFreeVideos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable("id") Long id, @RequestBody VideoDto responseDto) {
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
