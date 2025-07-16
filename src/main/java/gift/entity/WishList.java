package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "wishlist")
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_Id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_Id")
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

    protected WishList() {}

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
