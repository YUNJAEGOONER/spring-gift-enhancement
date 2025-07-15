package gift.service;

import gift.dto.wish.WishRequestDto;
import gift.dto.wish.WishResponseDto;
import gift.entity.Product;
import gift.entity.WishList;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    //단일 WishResponseDto가
    public WishResponseDto addToWishList(Long memberId, WishRequestDto requestDto) {

        //이미 장바구니에 해당 상품이 있는 경우에는 수량만 업데이트
        Optional<WishList> wishListOptional = wishListRepository.findWishListByMemberIdAndProductId(
                memberId, requestDto.productId());
        Product product = productRepository.findProductById(requestDto.productId()).get();

        if (wishListOptional.isEmpty()) {
            //수량만 바꿔서
            WishList wishList = wishListOptional.get();
            wishList.updateQuantity(requestDto.quantity());
            wishListRepository.save(wishList);
            return toWishResponseDto(wishList, product);
        }
        WishList wishList = wishListRepository.save(new WishList(memberId, requestDto.productId(), requestDto.quantity()));
        return toWishResponseDto(wishList, product);
    }

    public WishResponseDto toWishResponseDto(WishList wishList, Product product){
        return new WishResponseDto(wishList.getId(), product.getName(), product.getImageUrl(), wishList.getQuantity(), product.getPrice());
    }


    public List<WishResponseDto> getList(Long memeberId){
        List<WishList> wishListList = wishListRepository.findWishListByMemberId(memeberId);
        List<WishResponseDto> responseDtoList = new ArrayList<>();
        for(WishList wishList : wishListList){
            Product product = productRepository.findProductById(wishList.getProductId()).get();
            responseDtoList.add(toWishResponseDto(wishList, product));
        }
        return responseDtoList;
    }

    public void removeFromWishList(Long wishListId){
        wishListRepository.removeWishListById(wishListId);
    }

    public List<WishResponseDto> changeQuantity(Long memberId, Long wishListId, int amount){
        Optional<WishList> optionalWishList = wishListRepository.findWishListById(wishListId);
        if(optionalWishList.isEmpty()){
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        WishList wishList = optionalWishList.get();
        wishList.updateQuantity(amount);
        wishListRepository.save(wishList);
        return getList(memberId);
    }


}
