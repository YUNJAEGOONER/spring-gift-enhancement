package gift.wishlist.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.Wishlist.repository.WishListRepository;
import gift.member.Member;
import gift.Wishlist.WishList;
import gift.member.repository.MemberRepository;
import gift.product.Product;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class WishListRepositoryTest {

    @Autowired private WishListRepository wishListRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private ProductRepository productRepository;

    @Test
    void 위시리스트_생성(){
        Member member = memberRepository.save(new Member("declan@arsenal.com", "coyg"));
        Product product = productRepository.save(new Product("Galaxy Book", 1500000, "image"));
        WishList wishList = wishListRepository.save(new WishList(member, product, 2));
        assertThat(wishList.getId()).isNotNull();
        assertThat(wishList.getQuantity()).isEqualTo(2);
        assertThat(wishList.getProduct()).isEqualTo(product);
        assertThat(wishList.member()).isEqualTo(member);
    }

    @Test
    void 아이디로_위시리스트_검색() {
        Member member = memberRepository.save(new Member("declan@arsenal.com", "coyg"));
        Product product = productRepository.save(new Product("Galaxy Book", 1500000, "image"));
        WishList wishList = wishListRepository.save(new WishList(member, product, 2));

        Long wishListId = wishList.getId();
        Optional<WishList> find = wishListRepository.findWishListById(wishListId);

        assertThat(find).isNotEmpty();
        assertThat(find.get().getId()).isEqualTo(wishListId);
        assertThat(find.get().getProduct()).isEqualTo(product);
    }

    @Test
    void 위시리스트_아이디를_통한_위시리스트_삭제() {
        Member member = memberRepository.save(new Member("declan@arsenal.com", "coyg"));
        Product product = productRepository.save(new Product("Galaxy Book", 1500000, "image"));
        WishList wishList = wishListRepository.save(new WishList(member, product, 2));

        Long wishListId = wishList.getId();
        Optional<WishList> find = wishListRepository.findWishListById(wishListId);

        assertThat(find).isNotEmpty();
        assertThat(find.get().getId()).isEqualTo(wishListId);
        assertThat(find.get().getProduct()).isEqualTo(product);
    }

    @Test
    void 위시리스트_조회() {
        Member member = memberRepository.save(new Member("declan@arsenal.com", "coyg"));
        Product product1 = productRepository.save(new Product("Galaxy Book", 1500000, "image"));

        wishListRepository.save(new WishList(member, product1, 5));
        Product product2 = productRepository.save(new Product("LG Gram", 1200000, "image"));
        wishListRepository.save(new WishList(member, product2, 3));

        Pageable pageable = PageRequest.of(0, 10);
        List<WishList> wishListByMember = wishListRepository.findWishListByMemberId(pageable, member.getMemberId()).getContent();
        assertThat(wishListByMember.size()).isEqualTo(2);
    }

    @Test
    void 특정_상품이_위시리스트에_존재하는지_확인() {
        Member member = memberRepository.save(new Member("declan@arsenal.com", "coyg"));
        Product product = productRepository.save(new Product("Galaxy Book", 1500000, "image"));

        assertThat(wishListRepository.findWishListByMemberIdAndProductId(member.getMemberId(), product.getId())).isEmpty();

        WishList wishList = wishListRepository.save(new WishList(member, product, 5));

        Optional<WishList> wishListOptional = wishListRepository.findWishListByMemberIdAndProductId(member.getMemberId(), product.getId());

        assertThat(wishListOptional).isNotEmpty();
        assertThat(wishListOptional.get().getProduct()).isEqualTo(product);
        assertThat(wishListOptional.get().getQuantity()).isEqualTo(5);
        assertThat(wishListOptional.get()).isEqualTo(wishList);
    }
}