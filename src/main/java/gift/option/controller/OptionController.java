package gift.option.controller;

import gift.option.dto.OptionRequestDto;
import gift.option.dto.OptionResponseDto;
import gift.option.service.OptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    @Autowired OptionService optionService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<OptionResponseDto> createOption(
            @PathVariable Long productId,
            @RequestBody OptionRequestDto optionRequestDto
    ){
        OptionResponseDto responseDto = optionService.createOption(productId, optionRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<OptionResponseDto>> getOptions(@PathVariable Long productId){
        return ResponseEntity.ok(optionService.getOptionByProduct(productId));
    }


}
