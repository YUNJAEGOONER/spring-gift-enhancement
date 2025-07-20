package gift.option;

import gift.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.criteria.CriteriaBuilder.In;

@Entity
public class Option {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String name;

    private Integer quantity;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "product_id") //name_of_FK
    private Product product;

    public void changeOption(String name, Integer quantity, Integer price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void addStock(int amount){
        quantity += amount;
    }

    public void removeStock(int amount){
        quantity -= amount;
    }

    protected Option(){}

    public Option(String name, Integer quantity, Integer price, Product product){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }
}
