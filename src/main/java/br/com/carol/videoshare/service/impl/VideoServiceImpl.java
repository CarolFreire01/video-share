package br.com.carol.videoshare.service.impl;

import br.com.carol.videoshare.dto.VideoRequestDto;
import br.com.carol.videoshare.dto.VideoResponseDto;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.expections.ObjectNotFoundExpection;
import br.com.carol.videoshare.repository.VideoRepository;
import br.com.carol.videoshare.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoRepository videoRepository;


    @Override
    public VideoRequestDto addVideo(VideoRequestDto requestVideo) {
        Video video = this.dtoToEntityRequest(requestVideo);
        Video saveNewVideo = videoRepository.save(video);

        return new VideoRequestDto(saveNewVideo);
    }


    @Override
    public Optional<Video> findVideoById(Long id){
       return videoRepository.findById(id);
    }

    @Override
    public List<Video> findAllVideos(){
      return videoRepository.findAll();
    }

    @Override
    public VideoResponseDto updateVideo(VideoResponseDto videoResponseDto, Long id) {
       if (videoRepository.findById(id).isPresent()){
           Video existingVideo = videoRepository.findById(id).get();

           existingVideo.setTitle(videoResponseDto.getTitle());
           existingVideo.setDescription(videoResponseDto.getDescription());
           existingVideo.setUrlVideo(videoResponseDto.getUrlVideo());

           Video updatedVideo = videoRepository.save(existingVideo);

           return new VideoResponseDto(updatedVideo);
       }
       return null;
    }

    @Override
    public void deleteVideo(Long id){
        videoRepository.deleteById(id);
    }


    private Video dtoToEntityRequest(VideoRequestDto requestDto) {
        Video video = new Video();
        BeanUtils.copyProperties(requestDto, video);
        return video;
    }

}
