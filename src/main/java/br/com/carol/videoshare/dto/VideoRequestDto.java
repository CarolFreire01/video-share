package br.com.carol.videoshare.dto;

import br.com.carol.videoshare.entities.Video;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequestDto extends Video {

    private String title;
    private String description;
    private String urlVideo;

    public VideoRequestDto(Video video){
        BeanUtils.copyProperties(video, this);
    }
}
