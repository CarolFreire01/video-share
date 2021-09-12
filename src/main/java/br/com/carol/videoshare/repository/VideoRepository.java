package br.com.carol.videoshare.repository;

import br.com.carol.videoshare.entities.Category;
import br.com.carol.videoshare.entities.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM videos v where v.title LIKE :title%")
    Page<Video> findByTitleLike(String title, Pageable pageable);

    List<Video> findByCategory(Category category, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from videos v order by v.id limit :number")
    List<Video> findVideosFree(@Param("number") Byte quantity);

    Video findVideoById(Long id);
}
