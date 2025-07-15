package gift.repository;

import gift.dto.ProductRequestDto;
import gift.entity.Product;
import java.lang.reflect.Member;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long id);
    void removeProductById(Long id);
    List<Product> findProductByNameLike(String name);


//    public void modifyProduct(Long id, ProductRequestDto requestDto){
//        String sql = "update products set name = ?, price =?, image_url = ? where id =?";
//        jdbcTemplate.update(sql, requestDto.getName(), requestDto.getPrice(), requestDto.getImageUrl(), id);
//    }

}
