package br.com.carol.videoshare.repository;

import br.com.carol.videoshare.dto.VideoDto;
import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v.id, v.title, v.description FROM Video v where v.title LIKE :title%")
    Page<VideoDto> findByTitleLike(String title, Pageable pageable);

    Page<Video> findByCategory(Optional<Category> category, Pageable pageable);
}
