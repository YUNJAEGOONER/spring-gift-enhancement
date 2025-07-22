package gift.option.service;

import gift.exception.ErrorCode;
import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.entity.Option;
import gift.option.exception.OptionNotFound;
import gift.option.exception.OptionPriceError;
import gift.option.exception.UnavailableOptionName;
import gift.option.repository.OptionRepository;
import gift.product.entity.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    public OptionService(OptionRepository optionRepository, ProductRepository productRepository) {
        this.optionRepository = optionRepository;
        this.productRepository = productRepository;
    }

    public OptionResponseDto createOptionByProductId(Long productId, OptionRequestDto requestDto) {
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        //상품기본가격 + 옵션가 = 음수가 되는 경우
        if (product.getPrice() + requestDto.price() < 0) {
            throw new OptionPriceError(ErrorCode.UNAVAILABLE_OPTION_PRICE);
        }

        //동일 상품에 대해 동일한 옵션은 등록 불가
        optionRepository.findOptionByProduct_IdAndName(product.getId(), requestDto.name())
                .ifPresent(productOption -> {
                    throw new UnavailableOptionName(ErrorCode.UNAVAILABLE_OPTION_NAME);
                });

        //연관관계 편의 메서드
        Option option = optionRepository.save(new Option(requestDto.name(), requestDto.quantity(), requestDto.price(), product));
        option.setProduct(product);

        return new OptionResponseDto(
                option.getId(),
                option.getName(),
                option.getQuantity(),
                option.getPrice());
    }

    @Transactional(readOnly = true)
    public List<OptionResponseDto> getOptionByProduct(Long productId) {
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return optionRepository.findOptionByProduct_Id(product.getId())
                .stream()
                .map(productOption -> new OptionResponseDto(
                        productOption.getId(),
                        productOption.getName(),
                        productOption.getQuantity(),
                        productOption.getPrice()))
                .toList();
    }

    @Transactional(readOnly = true)
    public OptionResponseDto findOne(Long optionId) {
        Option option = optionRepository.findOptionById(optionId)
                .orElseThrow(() -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND));
        return new OptionResponseDto(option.getId(), option.getName(), option.getQuantity(),
                option.getPrice());
    }

    public OptionResponseDto updateOption(Long optionId, OptionRequestDto requestDto) {
        Option option = optionRepository.findOptionById(optionId).orElseThrow(
                () -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND));
        Optional<Option> optionOptional = optionRepository.findOptionByProduct_IdAndName(
                option.getProduct().getId(), requestDto.name());
        //해당 옵션명을 사용하고 있지 않거나, 해당 옵션명이 자신의 옵션명인 경우 (수량에만 변화가 생기는 경우)
        if (optionOptional.isEmpty() || optionOptional.get().getId().equals(optionId)) {
            option.changeOption(requestDto.name(), requestDto.quantity(), requestDto.price());
            return new OptionResponseDto(option.getId(), option.getName(), option.getQuantity(),
                    option.getPrice());
        }
        throw new UnavailableOptionName(ErrorCode.UNAVAILABLE_OPTION_NAME);
    }

    @Transactional
    public void removeOption(Long optionId) {
        Option option = optionRepository.findOptionById(optionId).orElseThrow(
                () -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND));
        optionRepository.removeOptionById(optionId);
    }

    @Transactional
    public Long getProductId(Long optionId){
        Option option = optionRepository.findOptionById(optionId).orElseThrow(
                () -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND)
        );
        return option.getProduct().getId();
    }

}
