package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findWishListById(Long id);
    void removeWishListById(Long id);
    List<WishList> findWishListByMemberId(Long memberId);
    Optional<WishList> findWishListByMemberIdAndProductId(Long memberId, Long productId);
}
