package demo.async_tangocard_integration.order;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.id = :id")
    Order findOneForUpdate(@Param("id") Long id);

    @Query("select o from Order o join o.user order by o.id desc")
    List<Order> findAllWithUsers();
    
}
