package gift.Wishlist.dto;

public record WishResponseDto(
        Long wishListId,
        String productName,
        String productImage,
        Integer quantity,
        Integer price
) {

}
