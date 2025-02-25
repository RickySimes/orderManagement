package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Data
@NoArgsConstructor
public class StockMovement {

    private LocalDateTime creationDate;

    private Item item;

    private Integer stock;

    private Integer remainingStock;

    public StockMovement(LocalDateTime creationDate, Item item, Integer stock, Integer remainingStock){
        this.creationDate = creationDate;
        this.item = item;
        this.stock = stock;
        this.remainingStock = remainingStock;
    }
}
