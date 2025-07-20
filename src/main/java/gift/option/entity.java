package gift.option;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class entity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String name;

    private Integer quantity;

    public void addStock(int amount){
        quantity += amount;
    }

    public void removeStock(int amount){
        quantity -= amount;
    }

}
