package gift.product.controller.api;

import gift.product.dto.ProductRequestDto;
import gift.product.Product;
import gift.exception.ErrorCode;
import gift.exception.MyException;
import gift.exception.page.PageIndexException;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@ResponseBody + @Controller
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    //create
    //생성한 product는 HashMap에 저장
    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(
            @RequestBody @Valid ProductRequestDto requestDto
    ) {
        Long id = productService.add(requestDto);
        return ResponseEntity.created(URI.create("api/products/" + id)).build();
    }

    //read
    //특정 상품을 조회(id)
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.findOne(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //read
    //전체 상품을 조회
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page, //현재 페이지
            @RequestParam(defaultValue = "5") int size //크기(몇개의 상품을 가져올지)
    ) {
        if(page < 0 || size < 0){
            throw new PageIndexException(ErrorCode.PAGE_INDEX_ERROR);
        }
        Page<Product> productPage = productService.findAll(page, size);
        return new ResponseEntity<>(productPage.getContent(), HttpStatus.OK);
    }

    //update
    //상품 수정
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> modifyProduct(
            @RequestBody @Valid ProductRequestDto requestDto,
            @PathVariable Long id
    ) {
        productService.modify(id, requestDto);
        Product modifiedProduct = productService.findOne(id);
        return new ResponseEntity<>(modifiedProduct, HttpStatus.OK);
    }

    //delete
    //등록된 상품을 삭제
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity<String> MemberControllerExceptionHandler(MyException e){
        return ResponseEntity.status(e.getErrorCode().getStatusCode()).body(e.getErrorCode().getMessage());
    }

}