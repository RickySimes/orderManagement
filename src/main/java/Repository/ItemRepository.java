package Repository;

import Entity.Item;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository {
    Optional<Item> findByName(String name);
}
