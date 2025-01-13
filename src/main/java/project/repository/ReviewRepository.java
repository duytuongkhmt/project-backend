package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository  extends CrudRepository<Review, String>, JpaSpecificationExecutor<Review> {

    List<Review> findByArtistId(String id);
    Review findByOrderId(String id);
}
