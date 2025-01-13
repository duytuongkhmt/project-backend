package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.PostMedia;

import java.util.List;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, String> {

    List<PostMedia> findByPostIdIs(String postId);
    void deleteByPostIdIs(String postId);

}
