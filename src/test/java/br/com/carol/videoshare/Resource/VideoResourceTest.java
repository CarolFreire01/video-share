package br.com.carol.videoshare.Resource;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;
import br.com.carol.videoshare.repository.VideoRepository;
import br.com.carol.videoshare.resources.VideoResource;
import br.com.carol.videoshare.service.impl.VideoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoResourceTest {


//
//    @Test
//    public void getAllVideosTest_Success(){
//        List<Video> videoList = new ArrayList<>();
//
//        Video video1 = new Video(Long.parseLong("1"), "Teste 1", "Video api teste", "www.teste.com");
//        Video video2 = new Video(Long.parseLong("2"), "Teste 2", "Video api teste 2", "www.teste2.com", "1");
//
//        videoList.add(video1);
//        videoList.add(video2);
//
//        VideoServiceImpl service = Mockito.mock(VideoServiceImpl.class);
//        when(service.findAllVideos()).thenReturn(videoList);
//
//        List<Video> expected = service.findAllVideos();
//
//        assertEquals(expected, videoList);
//    }
//
//    @Test
//    public void getVideoByID_Success(){
//
//        Optional<Video> video = Optional.of(new Video(Long.parseLong("1"), "Teste 2", "Video api teste 2", "www.teste2.com"));
//
//        VideoServiceImpl service = Mockito.mock(VideoServiceImpl.class);
//        when(service.findVideoById(1L)).thenReturn(video);
//
//        Optional<Video> expected = service.findVideoById(1L);
//
//        assertEquals(expected, video);
//    }

//    @Test
//    public void deleteVideo_Success(){
//
//        Optional<Video> video = Optional.of(new Video(Long.parseLong("1"), "Teste 2", "Video api teste 2", "www.teste2.com", Long.parseLong("1")));
//
//        VideoServiceImpl service = Mockito.mock(VideoServiceImpl.class);
//        when(service.findVideoById(1L)).thenReturn(video);
//
//        service.deleteVideo(1L);
//    }

    @Test
    public void saveVideo_Success() {

        VideoDto video = new VideoDto("Teste 1", "Video api teste", "www.teste.com", Long.parseLong("1"));

        VideoServiceImpl service = Mockito.mock(VideoServiceImpl.class);
        when(service.addVideo(ArgumentMatchers.any(VideoDto.class))).thenReturn(video);

        VideoDto expected = service.addVideo(video);

        assertEquals(expected, video);
    }

    @Test
    public void updateVideo_Success() {

        VideoDto video = new VideoDto("Teste 1", "Video api teste", "www.teste.com", Long.parseLong("1"));

        VideoDto newVideo = new VideoDto("Teste 2", "Video api teste 2", "www.teste.com", Long.parseLong("1"));

        VideoServiceImpl service = Mockito.mock(VideoServiceImpl.class);
        when(service.findVideoById(video.getId())).thenReturn(Optional.of(video));

        service.updateVideo(newVideo, video.getId());

    }


}
