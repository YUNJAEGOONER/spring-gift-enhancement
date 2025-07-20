package gift.product.service;

import gift.exception.ErrorCode;
import gift.product.Product;
import gift.product.dto.ProductOptionRequestDto;
import gift.product.dto.ProductRequestDto;
import gift.product.exception.ProductNotFoundException;
import gift.product.repository.ProductRepository;
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
    public Product add(ProductOptionRequestDto requestDto){
        Product product = new Product(requestDto.getName(), requestDto.getPrice(), requestDto.getImageUrl());
        return productRepository.save(product);
    }

    //상품 검색(id로)
    @Transactional(readOnly = true)
    public Product findOne(Long id){
        Product product = productRepository.findProductById(id).orElseThrow(()-> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return product;
    }

    //상품 검색
    @Transactional(readOnly = true)
    public Page<Product> searchProduct(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductByNameContaining(name, pageable);
    }

    //전체 상품 검색
    @Transactional(readOnly = true)
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
