package gift.service;

import gift.dto.ProductRequestDto;
import gift.entity.Product;
import gift.exception.ErrorCode;
import gift.exception.product.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //상품 추가
    public Long add(ProductRequestDto requestDto){
        Product product = new Product(requestDto.getName(), requestDto.getPrice(), requestDto.getImageUrl());
        return productRepository.save(product).getId();
    }

    //상품 검색
    public Product findOne(Long id){
        Product product = productRepository.findProductById(id).orElseThrow(()-> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return product;
    }

    //상품 검색
    public Page<Product> searchProduct(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductByNameContaining(name, pageable);
    }

    //전체 상품 검색
    public Page<Product> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    //상품 수정
    public void modify(Long id, ProductRequestDto requestDto){
        Product product = findOne(id);
        product.changeProductInfo(requestDto.getName(), requestDto.getPrice(), requestDto.getImageUrl());
        productRepository.save(product);
    }

    //상품 삭제
    public void remove(Long id){
        productRepository.removeProductById(id);
    }

}
