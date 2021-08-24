package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class VideoResource {

    private final VideoService videoService;

    @PostMapping(path = "/videos")
    public ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto requestDto) {
        VideoDto requestVideo = videoService.addVideo(requestDto);
        return Objects.nonNull(requestVideo) ? ResponseEntity.status(HttpStatus.CREATED).body(requestVideo) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Optional<Video>> findVideo(@PathVariable("id") Long id) {
        Optional<Video> video = videoService.findVideoById(id);
        return Objects.nonNull(video) ? ResponseEntity.status(HttpStatus.OK).body(video) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping("/videos")
    public ResponseEntity<Page<Video>> findAllVideos(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int limit) {
        Page<Video> video = videoService.findAllVideos(offset, limit);
        return Objects.nonNull(video) ? ResponseEntity.status(HttpStatus.OK).body(video) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/videos/name={title}")
    public ResponseEntity<Page<VideoDto>> findVideoByName(@PathVariable("title") String title, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int limit) {
        Page<VideoDto> video = videoService.findVideoByTitle(title, offset, limit);
        return Objects.nonNull(video) ? ResponseEntity.status(HttpStatus.OK).body(video) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/videos/free")
    public ResponseEntity<List<Video>> findVideosFree() {
        List<Video> freeVideos = videoService.listFreeVideos();
        return Objects.nonNull(freeVideos) ? ResponseEntity.status(HttpStatus.OK).body(freeVideos) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/videos/{id}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable("id") Long id, @RequestBody VideoDto responseDto) {
        VideoDto updateVideo = videoService.updateVideo(responseDto, id);
        return Objects.nonNull(updateVideo) ? ResponseEntity.status(HttpStatus.OK).body(responseDto) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<Void> updateVideo(@PathVariable("id") Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

}
