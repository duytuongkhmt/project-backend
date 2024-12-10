package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, String>, JpaSpecificationExecutor<Post> {

    Post findByIdIs(String id);
}
