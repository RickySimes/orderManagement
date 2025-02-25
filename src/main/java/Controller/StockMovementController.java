package Controller;


import Entity.StockMovement;
import Service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockMovementController {
    private final StockMovementService stockService;

    @Autowired
    public StockMovementController(StockMovementService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<StockMovement> createStockMovement(@Valid @RequestBody Map<String, Object> payload) {
        try {
            Long itemId = Long.valueOf(payload.get("itemId").toString());
            Integer quantity = Integer.valueOf(payload.get("quantity").toString());

            if (quantity <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            StockMovement stockMovement = stockService.createStockMovement(itemId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(stockMovement);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovement> getStockMovement(@PathVariable Long id) {
        try {
            StockMovement stockMovement = stockService.getStockMovement(id);
            return ResponseEntity.status(HttpStatus.OK).body(stockMovement);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<StockMovement>> getAllStockMovements() {
        List<StockMovement> stockMovements = stockService.getAllStockMovements();
        return ResponseEntity.status(HttpStatus.OK).body(stockMovements);
    }

}
