package br.com.carol.videoshare.resources;

import br.com.carol.videoshare.dto.VideoRequestDto;
import br.com.carol.videoshare.dto.VideoResponseDto;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.service.impl.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class VideoResource {

    @Autowired
    VideoServiceImpl service;

    @PostMapping(path = "/video")
    public ResponseEntity<VideoRequestDto> createVideo(@RequestBody VideoRequestDto requestDto) {
        VideoRequestDto requestVideo = service.addVideo(requestDto);
        return Objects.nonNull(requestVideo) ? ResponseEntity.status(HttpStatus.CREATED).body(requestVideo) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<Optional<Video>> findVideo(@PathVariable("id") Long id) {
        Optional<Video> video = service.findVideoById(id);
        return Objects.nonNull(video) ? ResponseEntity.status(HttpStatus.OK).body(video) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @GetMapping("/videos")
    public ResponseEntity<List<Video>> findAllVideo() {
        List<Video> video = service.findAllVideos();
        return Objects.nonNull(video) ? ResponseEntity.status(HttpStatus.OK).body(video) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/video/{id}")
    public ResponseEntity<VideoResponseDto> updateVideo(@PathVariable("id") Long id, @RequestBody VideoResponseDto responseDto) {
        VideoResponseDto updateVideo = service.updateVideo(responseDto, id);
        return Objects.nonNull(updateVideo) ? ResponseEntity.status(HttpStatus.OK).body(responseDto) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/video/{id}")
    public ResponseEntity<Void> updateVideo(@PathVariable("id") Long id) {
        service.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
