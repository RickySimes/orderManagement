package Service;

import Entity.Item;
import Entity.StockMovement;
import Repository.ItemRepository;
import Repository.StockMovementRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMovementService {
    private static final Logger logger = LoggerFactory.getLogger(StockMovementService.class);

    private final StockMovementRepository stockMovementRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public StockMovementService(StockMovementRepository stockMovementRepository,
                                ItemRepository itemRepository
                                ) {
        this.stockMovementRepository = stockMovementRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public StockMovement createStockMovement(Long itemId, Integer quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        StockMovement stockMovement = new StockMovement(item, quantity);
        stockMovement = stockMovementRepository.save(stockMovement);

        logger.info("Stock movement created: {}", stockMovement);
        return stockMovementRepository.save(stockMovement);
    }

    public StockMovement getStockMovement(Long id) {
        return stockMovementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock movement not found"));
    }
    public List<StockMovement> getAllStockMovements() {
        return stockMovementRepository.findAll();
    }
}
