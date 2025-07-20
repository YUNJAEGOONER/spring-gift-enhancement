package gift.option.service;

import gift.entity.Product;
import gift.exception.ErrorCode;
import gift.exception.MyException;
import gift.exception.product.ProductNotFoundException;
import gift.option.Option;
import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.repository.OptionRepository;
import gift.repository.ProductRepository;
import java.util.List;
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

        optionRepository.findOptionByProduct_IdAndName(product.getId(), requestDto.name())
                .ifPresent(option -> {throw new IllegalStateException("이미 등록되어 있는 옵션입니다.");});

        Option option = optionRepository.save(new Option(requestDto.name(), requestDto.quantity(), requestDto.price(), product));
        return new OptionResponseDto(
                option.getId(),
                option.getName(),
                option.getQuantity(),
                option.getPrice());
    }


    public List<OptionResponseDto> getOptionByProduct(Long productId){
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return optionRepository.findOptionByProduct_Id(product.getId())
                .stream()
                .map(option -> new OptionResponseDto(
                        option.getId(),
                        option.getName(),
                        option.getQuantity(),
                        option.getPrice()))
                .toList();
    }

}
