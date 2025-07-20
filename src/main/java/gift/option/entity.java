package gift.option;

import gift.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class entity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String name;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id") //name_of_FK
    private Product product;

    public void addStock(int amount){
        quantity += amount;
    }

    public void removeStock(int amount){
        quantity -= amount;
    }

}
