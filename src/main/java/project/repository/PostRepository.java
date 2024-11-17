package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

}
