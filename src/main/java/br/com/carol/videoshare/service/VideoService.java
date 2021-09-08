package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundException;
import br.com.carol.videoshare.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VideoService {

    final private VideoRepository videoRepository;

    public VideoDto addVideo(VideoDto videoDto) {
        validateRequest(videoDto);

        Video video = this.dtoToEntityRequest(videoDto);
        Video saveNewVideo = videoRepository.save(video);

        return new VideoDto(saveNewVideo);
    }

    public List<VideoDto> findVideoByTitle(String title, Pageable pageable){
        if (title.length() < 3){
            throw new BadRequestException("title cannot be less than 3 characters");
        }

        Page<Video> allVideosByTitle = videoRepository.findByTitleLike(title, pageable);

        if (allVideosByTitle.isEmpty()){
            throw new ObjectNotFoundException("There is no video with that name");
        }

        return allVideosByTitle.stream()
                .map(this::buildVideos)
                .collect(Collectors.toList());
    }

    public List<VideoDto> findAllVideos(Pageable pageable){
        Page<Video> allVideos = videoRepository.findAll(pageable);

        if (allVideos.isEmpty()){
            throw new ObjectNotFoundException("There is no video with that name");
        }

        return allVideos.stream()
                .map(this::buildVideos)
                .collect(Collectors.toList());
    }

    public Optional<Video> findVideoById(Long id){
        try {
            return videoRepository.findById(id);
        } catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Video not found");
        }
    }

    public VideoDto updateVideo(VideoDto videoDto, Long id) {
       validateRequest(videoDto);

       if (videoRepository.findById(id).isPresent()){
           Video existingVideo = videoRepository.findById(id).get();

          buildVideos(existingVideo);

           Video updatedVideo = videoRepository.save(existingVideo);

           return new VideoDto(updatedVideo);
       } else {
           throw new ObjectNotFoundException("Video not found");
       }
    }

    public void deleteVideo(Long id){
            videoRepository.deleteById(id);
    }

    public List<Video> listFreeVideos() {
        final Byte number = 10;
        return videoRepository.findVideosFree(number);
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

    private VideoDto buildVideos(Video video){
        return VideoDto.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .urlVideo(video.getUrlVideo())
                .category_id(video.getCategory().getId())
                .build();
    }

}
