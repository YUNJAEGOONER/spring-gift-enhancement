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

    Optional<WishList> findWishListByMemberIdAndId(Long memberId, Long id);
    

//    public WishResponseDto getWishProduct(Long memberId, Long productId){
//        String sql = "select w.id, p.name, p.image_url, w.quantity, p.price from Products p, WishList w, Members m "
//                + "where w.memberid = m.id and w.productid = p.id and m.id = ? and p.id = ? ";
//        List<WishResponseDto> mywishList = jdbcTemplate.query(sql, wishResponseDtoRowMapper(), memberId, productId);
//        return mywishList.get(0);
//    }

//    public Long getProductIdByWithId(Long wishListId){
//        String sql = "select productid from whishlist where id = ?";
//        return jdbcTemplate.queryForObject(sql, Long.class, wishListId);
//    }


//    public void updateQuantity(Long memberId, Long wishListId, int amount){
//        String sql = "select quantity from wishlist where memberid = ? and id = ?";
//        Integer quantity = jdbcTemplate.queryForObject(sql, Integer.class, memberId, wishListId);
//
//        Integer updateQuantity = quantity + amount;
//
//        if(updateQuantity == 0){
//            remove(wishListId);
//            return;
//        }
//
//        String udpateSql = "update wishlist set quantity = ? where memberid = ? and id = ?";
//        jdbcTemplate.update(udpateSql, updateQuantity, memberId, wishListId);
//    }


}
