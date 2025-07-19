package gift.controller.view;

import gift.entity.Product;
import gift.exception.ErrorCode;
import gift.exception.page.PageIndexException;
import gift.exception.product.ProductNotFoundException;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/view")
@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService){
        this.productService = productService;
    }

    //전체 상품을 조회
    @GetMapping("/products/list")
    public String getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        if(page < 0 || size < 0){
            throw new PageIndexException(ErrorCode.PAGE_INDEX_ERROR);
        }
        Page<Product> productList = productService.findAll(page, size);
        model.addAttribute("productList", productList);
        return "/yjshop/user/home";
    }

    //특정 상품을 조회(id)
    @GetMapping("/products/info")
    public String getProduct(
            @RequestParam(required = false) Long id,
            Model model
    ) {
        Product product = productService.findOne(id);
        model.addAttribute("product", product);
        return "/yjshop/user/productinfo";
    }

    //특정 상품을 검색(상품명을 통한 검색)
    @GetMapping("/products/search")
    public String searchProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            Model model
    ) {
        //상품 검색하기에 아무런 상품명를 입력하지 않은 경우 -> 전체 상품을 조회하는 페이지로 이동
        if(name.isBlank()){
            return "redirect:/view/products/list";
        }
        if(page < 0 || size < 0){
            throw new PageIndexException(ErrorCode.PAGE_INDEX_ERROR);
        }
        Page<Product> product = productService.searchProduct(name, page, size);
        model.addAttribute("productList", product);
        model.addAttribute("searchkeyword", name);
        return "/yjshop/user/search";
    }

}