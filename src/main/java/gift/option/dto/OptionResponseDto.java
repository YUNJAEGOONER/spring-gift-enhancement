package gift.option.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public record OptionResponseDto(
        Long optionId,
        String productName,
        String optionName,
        Integer quantity,
        Integer price
){ }
