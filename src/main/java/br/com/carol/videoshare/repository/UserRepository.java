package br.com.carol.videoshare.repository;

import br.com.carol.videoshare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
