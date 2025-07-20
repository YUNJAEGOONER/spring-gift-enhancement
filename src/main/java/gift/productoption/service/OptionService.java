package gift.productoption.service;

import gift.product.Product;
import gift.exception.ErrorCode;
import gift.product.exception.ProductNotFoundException;
import gift.productoption.ProductOption;
import gift.productoption.dto.OptionRequestDto;
import gift.productoption.dto.OptionResponseDto;
import gift.productoption.exception.OptionNotFound;
import gift.productoption.exception.OptionPriceError;
import gift.productoption.exception.UnavailableOptionName;
import gift.productoption.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionService {

    @Autowired private OptionRepository optionRepository;
    @Autowired private ProductRepository productRepository;

    public OptionResponseDto createOptionByProductId(Long productId, OptionRequestDto requestDto){
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        if(product.getPrice() + requestDto.price() < 0){
            throw new OptionPriceError(ErrorCode.UNAVAILABLE_OPTION_PRICE);
        }

        optionRepository.findOptionByProduct_IdAndName(product.getId(), requestDto.name())
                .ifPresent(productOption -> {throw new UnavailableOptionName(ErrorCode.UNAVAILABLE_OPTION_NAME);});

        ProductOption productOption = optionRepository.save(new ProductOption(requestDto.name(), requestDto.quantity(), requestDto.price(), product));
        return new OptionResponseDto(
                productOption.getId(),
                productOption.getName(),
                productOption.getQuantity(),
                productOption.getPrice());
    }

    public OptionResponseDto createOptionByProduct(Product product, OptionRequestDto requestDto){

        if(product.getPrice() + requestDto.price() < 0){
            throw new OptionPriceError(ErrorCode.UNAVAILABLE_OPTION_PRICE);
        }

        optionRepository.findOptionByProduct_IdAndName(product.getId(), requestDto.name())
                .ifPresent(productOption -> {throw new UnavailableOptionName(ErrorCode.UNAVAILABLE_OPTION_NAME);});

        ProductOption productOption = optionRepository.save(new ProductOption(requestDto.name(), requestDto.quantity(), requestDto.price(), product));
        return new OptionResponseDto(
                productOption.getId(),
                productOption.getName(),
                productOption.getQuantity(),
                productOption.getPrice());
    }

    @Transactional(readOnly = true)
    public List<OptionResponseDto> getOptionByProduct(Long productId){
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
    public OptionResponseDto findOne(Long optionId){
        ProductOption productOption = optionRepository.findOptionById(optionId)
                .orElseThrow(() -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND));
        return new OptionResponseDto(productOption.getId(), productOption.getName(), productOption.getQuantity(), productOption.getPrice());
    }

    public OptionResponseDto updateOption(Long optionId, OptionRequestDto requestDto){
        ProductOption productOption = optionRepository.findOptionById(optionId).orElseThrow(
                () -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND));
        Optional<ProductOption> optionOptional = optionRepository.findOptionByProduct_IdAndName(
                productOption.getProduct().getId(), requestDto.name());
        //해당 옵션명을 사용하고 있지 않거나, 해당 옵션명이 자신의 옵션명인 경우 (수량에만 변화가 생기는 경우)
        if(optionOptional.isEmpty()||optionOptional.get().getId().equals(optionId)){
            productOption.changeOption(requestDto.name(), requestDto.price(), requestDto.quantity());
            return new OptionResponseDto(productOption.getId(), productOption.getName(), productOption.getQuantity(), productOption.getPrice());
        }
        throw new UnavailableOptionName(ErrorCode.UNAVAILABLE_OPTION_NAME);
    }

    @Transactional
    public void removeOtion(Long optionId){
        ProductOption productOption = optionRepository.findOptionById(optionId).orElseThrow(
                () -> new OptionNotFound(ErrorCode.OPTION_NOT_FOUND));
        optionRepository.removeOptionById(optionId);
    }


}
