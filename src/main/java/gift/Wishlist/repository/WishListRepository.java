package gift.Wishlist.repository;

import gift.Wishlist.WishList;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findWishListById(Long id);
    void removeWishListById(Long id);
    @Query("SELECT w FROM WishList w JOIN FETCH w.product WHERE w.member.id = :memberId")
    Page<WishList> findWishListByMemberId(Pageable pageable, @Param("memberId") Long memberId);
    Optional<WishList> findWishListByMemberIdAndProductId(Long memberId, Long productId);
}

