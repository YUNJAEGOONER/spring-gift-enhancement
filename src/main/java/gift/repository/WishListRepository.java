package gift.repository;

import gift.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findWishListById(Long id);
    void removeWishListById(Long id);
    @Query("SELECT w FROM WishList w JOIN FETCH w.product WHERE w.member.id = :memberId")
    List<WishList> findWishListByMemberId(@Param("memberId") Long memberId);
    Optional<WishList> findWishListByMemberIdAndProductId(Long memberId, Long productId);
}

