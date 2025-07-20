package gift.wishlist;

import gift.member.Member;
import gift.productoption.ProductOption;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_Id")
    private ProductOption productOption;

    @Column(nullable = false)
    private Integer quantity;

    public WishList(Member member, ProductOption productOption, Integer quantity){
        this.member = member;
        this.productOption = productOption;
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

    public ProductOption getOption() {
        return productOption;
    }

    public Integer getQuantity(){
        return quantity;
    }
}
