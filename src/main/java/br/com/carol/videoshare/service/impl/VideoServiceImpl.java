package br.com.carol.videoshare.service.impl;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundExpection;
import br.com.carol.videoshare.repository.VideoRepository;
import br.com.carol.videoshare.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoRepository videoRepository;


    @Override
    public VideoDto addVideo(VideoDto videoDto) {
        validateRequest(videoDto);

        Video video = this.dtoToEntityRequest(videoDto);
        Video saveNewVideo = videoRepository.save(video);

        return new VideoDto(saveNewVideo);
    }

    @Override
    public Page<VideoDto> findVideoByTitle(String title, int offset, int limit){
        Page<VideoDto> findVideo = videoRepository.findByTitleLike(title, PageRequest.of(offset, limit));

        if (findVideo == null){
            throw new ObjectNotFoundExpection("There is no video with that name");
        }

        return findVideo;

    }

    @Override
    public Optional<Video> findVideoById(Long id){
       return videoRepository.findById(id);
    }

    public Page<Video> findAllVideos(int offset, int limit){
        Pageable paging = PageRequest.of(offset, limit);

        return videoRepository.findAll(paging);
    }

    @Override
    public VideoDto updateVideo(VideoDto videoDto, Long id) {
       validateRequest(videoDto);

       if (videoRepository.findById(id).isPresent()){
           Video existingVideo = videoRepository.findById(id).get();

           existingVideo.setTitle(videoDto.getTitle());
           existingVideo.setDescription(videoDto.getDescription());
           existingVideo.setUrlVideo(videoDto.getUrlVideo());

           Video updatedVideo = videoRepository.save(existingVideo);

           return new VideoDto(updatedVideo);
       } else {
           throw new ObjectNotFoundExpection("Video not found");
       }
    }

    @Override
    public void deleteVideo(Long id){
        videoRepository.deleteById(id);
    }

    private void validateRequest(VideoDto videoDto){
        if (StringUtils.isBlank(videoDto.getTitle())){
            throw new BadRequestException("Title is mandatory");
        } else if (StringUtils.isBlank(videoDto.getDescription())){
            throw new BadRequestException("Description is mandatory");
        } else if (StringUtils.isBlank(videoDto.getUrlVideo())){
            throw new BadRequestException("Url is mandatory");
        }
    }

    private Video dtoToEntityRequest(VideoDto requestDto) {
        Video video = new Video();
        BeanUtils.copyProperties(requestDto, video);
        return video;
    }

}
