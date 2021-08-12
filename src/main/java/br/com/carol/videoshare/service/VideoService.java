package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface VideoService {

    VideoDto addVideo(VideoDto videoRequestDto);

    Optional<Video> findVideoById(Long id);

    Page<VideoDto> findVideoByTitle(String title, int offset, int limit);

    Page<Video> findAllVideos(int offset, int limit);

    VideoDto updateVideo(VideoDto videoResponseDto, Long id);

    void deleteVideo(Long id);

    List<Video> listFreeVideos();
}
