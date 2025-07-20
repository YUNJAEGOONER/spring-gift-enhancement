package gift.option.controller.view;

import gift.option.dto.OptionResponseDto;
import gift.option.service.OptionService;
import gift.product.Product;
import gift.product.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class OptionViewController {

    @Autowired OptionService optionService;
    @Autowired ProductService productService;

    //상품아이디를 통해 특정 상품의 옵션을 모두 조회
    @GetMapping("/products/{productId}/options")
    public String getAllProductOptions(
            @PathVariable Long productId,
            Model model
    ){
        Product product = productService.findOne(productId);
        List<OptionResponseDto> options = optionService.getOptionByProduct(productId);
        model.addAttribute("product", product);
        model.addAttribute("options", options);
        return "yjshop/user/productoptions";
    }



}
