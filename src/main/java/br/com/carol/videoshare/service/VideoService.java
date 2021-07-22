package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.VideoRequestDto;
import br.com.carol.videoshare.dto.VideoResponseDto;
import br.com.carol.videoshare.entities.Video;

import java.util.List;
import java.util.Optional;


public interface VideoService {

    VideoRequestDto addVideo(VideoRequestDto videoRequestDto);

    Optional<Video> findVideoById(Long id);

    List<Video> findAllVideos();

    VideoResponseDto updateVideo(VideoResponseDto videoResponseDto, Long id);

    void deleteVideo(Long id);

}
