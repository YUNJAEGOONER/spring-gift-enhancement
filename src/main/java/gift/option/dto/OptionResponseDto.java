package gift.option.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public record OptionResponseDto(
        Long optionId,
        String optionName,
        Integer quantity,
        Integer price
){ }
