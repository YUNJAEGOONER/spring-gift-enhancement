package gift.option.entity;

import gift.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") //name_of_FK
    private Product product;

    public void changeOption(String name, Integer quantity, Integer price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void addStock(int amount) {
        quantity += amount;
    }

    public void removeStock(int amount) {
        quantity -= amount;
    }

    protected Option() {
    }

    public Option(String name, Integer quantity, Integer price, Product product) {
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

    //연관 관계 편의 메서드
    public void setProduct(Product product){
        if (Objects.nonNull(this.product)){
            this.product.getOptions().remove(this);
        }
        this.product = product;
        product.getOptions().add(this);
    }

}
