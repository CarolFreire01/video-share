package br.com.carol.videoshare.entities;

import br.com.carol.videoshare.dto.CategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@JsonIgnoreProperties({"videos"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String color;

    @OneToMany(mappedBy="category", cascade = CascadeType.MERGE)
    private Set<Video> videos;

    public void updateCategory(CategoryRequest categoryRequest) {
        if (categoryRequest.getTitle() != null && categoryRequest.getTitle() != "") {
            this.title = categoryRequest.getTitle();
        }

        if (categoryRequest.getColor() != null && categoryRequest.getColor() != "") {
            this.color = categoryRequest.getColor();
        }
    }
}
