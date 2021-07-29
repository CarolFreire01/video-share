package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Video;

import java.util.List;
import java.util.Optional;


public interface VideoService {

    VideoDto addVideo(VideoDto videoRequestDto);

    Optional<Video> findVideoById(Long id);

    List<VideoDto> findVideoByTitle(String title);

    List<Video> findAllVideos();

    VideoDto updateVideo(VideoDto videoResponseDto, Long id);

    void deleteVideo(Long id);

    List<Video> findVideosByCategoryId(Long category_id);

}
