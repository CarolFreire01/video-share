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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoRepository videoRepository;


    @Override
    public VideoDto addVideo(VideoDto videoDto) {
        validateRequest(videoDto);
//
//        if (videoDto.getCategory().getId() == null){
//            videoDto.getCategory().setId(Long.valueOf(1));
//        }

        Video video = this.dtoToEntityRequest(videoDto);
        Video saveNewVideo = videoRepository.save(video);

        return new VideoDto(saveNewVideo);
    }

    @Override
    public List<VideoDto> findVideoByTitle(String title){
        List<VideoDto> findVideo = videoRepository.findByTitleLike(title);

        if (findVideo == null){
            throw new ObjectNotFoundExpection("There is no video with that name");
        }

        return findVideo;

    }

    @Override
    public List<Video> findVideosByCategoryId(Long category_id){
        List<Video> findVideoByCategoryId = videoRepository.findVideosByCategory_Id(category_id);

        if (findVideoByCategoryId == null){
            throw new ObjectNotFoundExpection("No exists videos with this category id: " + category_id);
        } else if(findVideoByCategoryId.isEmpty()){
            throw new ObjectNotFoundExpection("Not exists videos with this category id " + category_id);
        }

        return findVideoByCategoryId;
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
