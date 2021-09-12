package br.com.carol.videoshare.dto;

import br.com.carol.videoshare.entities.Video;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoRequest {

    private Long id;
    private String title;
    private String description;
    private String urlVideo;
    private Long category_id;

}
