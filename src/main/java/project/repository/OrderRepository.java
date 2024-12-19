package project.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, String>, JpaSpecificationExecutor<Order> {
    List<Order> findAllByArtistIdAndStatusIsNot(String id,String status);
    List<Order> findAllByBookerIdAndStatusIsNot(String id,String status);

    Order findByIdIs(String id);

}
