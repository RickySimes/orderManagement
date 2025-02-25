package Repository;

import Entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);
}
