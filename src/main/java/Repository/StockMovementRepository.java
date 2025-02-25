package Repository;

import Entity.StockMovement;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockMovementRepository {
    Optional<StockMovement> findById(Long id);
}
