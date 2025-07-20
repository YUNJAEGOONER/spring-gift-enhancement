package gift.productoption.dto;

public record OptionResponseDto(
        Long id,
        String optionName,
        Integer quantity,
        Integer price
){ }
