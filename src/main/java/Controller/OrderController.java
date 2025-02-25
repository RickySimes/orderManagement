package Controller;

import Entity.Order;
import Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Map<String, Object> payload) {
        try {
            Long itemId = Long.valueOf(payload.get("itemId").toString());
            Integer quantity = Integer.valueOf(payload.get("quantity").toString());
            Long userId = Long.valueOf(payload.get("userId").toString());

            if (quantity <= 0) {
                return ResponseEntity.badRequest().build();
            }

            Order order = orderService.createOrder(itemId, quantity, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
                                             @Valid @RequestBody Map<String, Object> payload) {
        try {
            Integer quantity = Integer.valueOf(payload.get("quantity").toString());

            if (quantity <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Order order = orderService.updateOrder(id, quantity);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        try {
            Order order = orderService.getOrder(id);
            return ResponseEntity.status(HttpStatus.OK).body(order);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
