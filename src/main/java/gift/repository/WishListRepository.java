package gift.repository;

import gift.dto.wish.WishResponseDto;
import gift.entity.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findWishListById(Long id);
    void removeWishListById(Long id);
    List<WishList> findWishListByMemberId(Long memberId);
    Optional<WishList> findWishListByMemberIdAndProductId(Long memberId, Long productId);
}

