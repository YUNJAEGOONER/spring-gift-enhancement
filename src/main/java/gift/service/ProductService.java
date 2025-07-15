package gift.service;

import gift.dto.ProductRequestDto;
import gift.entity.Product;
import gift.exception.ErrorCode;
import gift.exception.MyException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
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
        Optional<Product> optionalProduct = productRepository.findProductById(id);
        if(optionalProduct.isEmpty()){
            throw new MyException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return optionalProduct.get();
    }

    //상품 검색
    public List<Product> searchProduct(String name){
        return productRepository.findProductByNameLike(name);
    }

    //전체 상품 검색
    public List<Product> findAll(){
        return productRepository.findAll();
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
