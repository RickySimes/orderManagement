package Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer remainingStock;

    public StockMovement(Item item, Integer stock){
        this.creationDate = LocalDateTime.now();
        this.item = item;
        this.stock = stock;
        this.remainingStock = stock;
    }
}
