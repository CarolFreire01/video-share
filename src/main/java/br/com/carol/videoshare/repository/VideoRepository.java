package br.com.carol.videoshare.repository;

import br.com.carol.videoshare.entities.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

}
