package gift.option.repository;

import gift.option.controller.view.Option;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findOptionByProduct_Id(Long productId);

    Optional<Option> findOptionByProduct_IdAndName(Long productId, String name);

    Optional<Option> findOptionById(Long id);

    void removeOptionById(Long id);
}
