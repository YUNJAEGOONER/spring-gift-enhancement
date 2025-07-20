package gift.option.service;

import gift.entity.Product;
import gift.exception.ErrorCode;
import gift.exception.product.ProductNotFoundException;
import gift.option.Option;
import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    @Autowired private OptionRepository optionRepository;
    @Autowired private ProductRepository productRepository;

    public OptionResponseDto createOption(Long productId, OptionRequestDto requestDto){
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        if(product.getPrice() + requestDto.price() < 0){
            throw new IllegalStateException("옵션 가격 문제 발생");
        }
        Option option = optionRepository.save(new Option(requestDto.name(), requestDto.quantity(), requestDto.price(), product));
        return new OptionResponseDto(
                option.getId(),
                option.getProduct().getName(),
                option.getName(),
                option.getQuantity(),
                option.getProduct().getPrice() + option.getPrice());
    }

}
