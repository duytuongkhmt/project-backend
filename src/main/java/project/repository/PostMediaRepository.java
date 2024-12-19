package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.PostMedia;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, String> {

}
