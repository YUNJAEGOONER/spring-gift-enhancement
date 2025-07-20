package gift.option.dto;

public record OptionResponseDto(
        Long optionId,
        String optionName,
        Integer quantity,
        Integer price
){ }
