package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Comment;

import java.util.List;


@Repository
public interface CommentRepository extends CrudRepository<Comment, String>, JpaSpecificationExecutor<Comment> {
    List<Comment> findByPostId(String id);
    void deleteByPostId(String id);
}
