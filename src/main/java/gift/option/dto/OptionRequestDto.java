package gift.option.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record OptionRequestDto(
        @NotBlank
        @Pattern(regexp = "^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9()\\[\\]\\+\\-\\&/_ ]+$", message = "특수문자는 ( ), [ ], +, -, &, /, _ 만 입력이 가능합니다.")
        String name,

        @NotNull
        @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
        @Max(value = 100_000_000, message = "옵션 수량은 1억 개 미만이어야 합니다.")
        Integer quantity
){

}