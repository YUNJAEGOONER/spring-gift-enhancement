package gift.repository;

import gift.dto.wish.WishRequestDto;
import gift.dto.wish.WishResponseDto;
import gift.entity.Product;
import gift.entity.WishList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    
    Optional<WishList> findWishListById(Long id);
    
    void removeWishListById(Long id);

    List<WishList> findWishListByMemberId(Long memberId);

    Optional<WishList> findWishListByMemberIdAndProductId(Long memberId, Long productId);

}
