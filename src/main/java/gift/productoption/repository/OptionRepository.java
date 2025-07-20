package gift.productoption.repository;

import gift.productoption.ProductOption;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findOptionByProduct_Id(Long productId);

    Optional<ProductOption> findOptionByProduct_IdAndName(Long productId, String name);

    Optional<ProductOption> findOptionById(Long id);

    void removeOptionById(Long id);
}
