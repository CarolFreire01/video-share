package br.com.carol.videoshare.entities;

import br.com.carol.videoshare.dto.VideoRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    private String description;

    private String urlVideo;

    @Column(name="category_id",nullable=true,insertable = false,updatable = false)
    private Long categoryId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

    public void addCategory(Category category) {
        this.category = category;
        this.categoryId = category.getId();
    }

    public void updateVideo(VideoRequest videoRequest) {

        if (videoRequest.getTitle() != null && videoRequest.getDescription() != ""){
            this.title = videoRequest.getTitle();
        }

        if (videoRequest.getDescription() != null && videoRequest.getDescription() != ""){
            this.description = videoRequest.getDescription();
        }

        if (videoRequest.getUrlVideo() != null && videoRequest.getDescription() != ""){
            this.urlVideo = videoRequest.getUrlVideo();
        }
    }

}
