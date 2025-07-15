package gift.controller.view;

import gift.entity.Product;
import gift.exception.ErrorCode;
import gift.exception.MyException;
import gift.exception.member.MemberNotFoundException;
import gift.exception.product.ProductNotFoundException;
import gift.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
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
    public String getProducts(Model model) {
        List<Product> productList = productService.findAll();
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
            @RequestParam(required = false) String name,
            Model model
    ) {
        //상품 검색하기에 아무런 상품명를 입력하지 않은 경우 -> 전체 상품을 조회하는 페이지로 이동
        if(name == null){
            return "redirect:/view/product/list";
        }

        List<Product> product = productService.searchProduct(name);
        model.addAttribute("productList", product);
        return "/yjshop/user/home";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String productNotFound(ProductNotFoundException e, Model model) {
        model.addAttribute("errorMsg", e.getErrorCode().getMessage());
        return "/yjshop/user/productnotfound";
    }

}

