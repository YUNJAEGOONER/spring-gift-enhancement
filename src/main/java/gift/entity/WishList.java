package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public WishList(Member member, Product product, Integer quantity){
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity){
        this.quantity += quantity;
    }

    public WishList() {

    }

    public Long getId() {
        return id;
    }

    public Member member() {
        return member;
    }

    public Product getProduct(){
        return product;
    }

    public Integer getQuantity(){
        return quantity;
    }
}
