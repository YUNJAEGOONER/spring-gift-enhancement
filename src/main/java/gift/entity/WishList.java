package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    private Long productId;

    private Integer quantity;

    public WishList(Long memberId, Long productId, Integer quantity){
        this.memberId = memberId;
        this.productId = productId;
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

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId(){
        return productId;
    }

    public Integer getQuantity(){
        return quantity;
    }
}
