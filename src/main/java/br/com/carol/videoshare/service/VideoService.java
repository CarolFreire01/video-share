package br.com.carol.videoshare.service;

import br.com.carol.videoshare.dto.VideoRequest;
import br.com.carol.videoshare.dto.VideoResponse;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.expections.BadRequestException;
import br.com.carol.videoshare.expections.ObjectNotFoundException;
import br.com.carol.videoshare.repository.CategoryRepository;
import br.com.carol.videoshare.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VideoService {

    final private VideoRepository videoRepository;
    final private CategoryService categoryService;
    final private CategoryRepository categoryRepository;

    public VideoResponse addVideo(VideoRequest videoRequest) {
        validateRequest(videoRequest);

        Category category = categoryService.findCategoryById(videoRequest.getCategory_id());

        Video video = dtoToEntityRequest(videoRequest);
        video.addCategory(category);

        Video saveNewVideo = videoRepository.save(video);

        return buildVideoResponse(saveNewVideo);
    }

    public List<VideoResponse> findVideoByTitle(String title, Pageable pageable){
        if (title.length() < 3){
            throw new BadRequestException("title cannot be less than 3 characters");
        }

        Page<Video> allVideosByTitle = videoRepository.findByTitleLike(title, pageable);

        if (allVideosByTitle.isEmpty()){
            throw new ObjectNotFoundException("There is no video with that name");
        }

        return allVideosByTitle.stream()
                .map(this::buildVideoResponse)
                .collect(Collectors.toList());
    }

    public List<VideoResponse> findAllVideos(Pageable pageable){
        Page<Video> allVideos = videoRepository.findAll(pageable);

        if (allVideos.isEmpty()){
            throw new ObjectNotFoundException("There is no video with that name");
        }

        return allVideos.stream()
                .map(this::buildVideoResponse)
                .collect(Collectors.toList());
    }

    public VideoResponse findVideoById(Long id){
        try {
            Video video = videoRepository.findVideoById(id);
            return buildVideoResponse(video);
        } catch (ObjectNotFoundException e){
            throw new ObjectNotFoundException("Video not found");
        }
    }

    public VideoResponse updateVideo(VideoRequest videoRequest, Long id) {
       Video existingVideo = videoRepository.findVideoById(id);

        existingVideo.updateVideo(videoRequest);

        Video updatedVideo = videoRepository.save(existingVideo);

        return buildVideoResponse(updatedVideo);
    }

    public void deleteVideo(Long id){
            videoRepository.deleteById(id);
    }

    public List<VideoResponse> listFreeVideos() {
        final Byte number = 10;
        List<Video> videosFree = videoRepository.findVideosFree(number);

        return videosFree.stream()
                .map(this::buildVideoResponse)
                .collect(Collectors.toList());
    }

    public List<VideoResponse> findVideosByCategoryId(Long id_category, Pageable pageable) {
        Category categories = categoryRepository.findCategoryById(id_category);

        if (categories == null){
            throw new ObjectNotFoundException("Id Category not found");
        }

        List<Video> findVideo =  videoRepository.findByCategory(categories, pageable);

        return findVideo.stream()
                .map(this::buildVideoResponse)
                .collect(Collectors.toList());
    }

    private void validateRequest(VideoRequest videoRequest){
        if (StringUtils.isBlank(videoRequest.getTitle())){
            throw new BadRequestException("Title is mandatory");
        } else if (StringUtils.isBlank(videoRequest.getDescription())){
            throw new BadRequestException("Description is mandatory");
        } else if (StringUtils.isBlank(videoRequest.getUrlVideo())){
            throw new BadRequestException("Url is mandatory");
        }
    }

    private Video dtoToEntityRequest(VideoRequest requestDto) {
        return Video.builder()
                .id(requestDto.getId())
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .urlVideo(requestDto.getUrlVideo())
                .categoryId(requestDto.getCategory_id())
                .build();
    }

    public VideoResponse buildVideoResponse(Video video){
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .urlVideo(video.getUrlVideo())
                .category_id(video.getCategory().getId())
                .build();
    }

}
