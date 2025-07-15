package gift.service;

import gift.dto.wish.WishRequestDto;
import gift.dto.wish.WishResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.WishList;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    //단일 WishResponseDto가
    public WishResponseDto addToWishList(Member member, WishRequestDto requestDto) {
        //이미 장바구니에 해당 상품이 있는 경우에는 수량만 업데이트
        Product product = productRepository.findProductById(requestDto.productId()).get();
        Optional<WishList> wishListOptional = wishListRepository.findWishListByMemberAndProduct(member, product);
        if (wishListOptional.isPresent()) {
            //수량만 바꿔서
            WishList wishList = wishListOptional.get();
            wishList.updateQuantity(requestDto.quantity());
            wishListRepository.save(wishList);
            return toWishResponseDto(wishList, product);
        }
        WishList wishList = wishListRepository.save(new WishList(member, product, requestDto.quantity()));
        return toWishResponseDto(wishList, product);
    }

    public WishResponseDto toWishResponseDto(WishList wishList, Product product){
        return new WishResponseDto(wishList.getId(), product.getName(), product.getImageUrl(), wishList.getQuantity(), product.getPrice());
    }

    public List<WishResponseDto> getList(Member member){
        List<WishList> wishListList = wishListRepository.findWishListByMember(member);
        List<WishResponseDto> responseDtoList = new ArrayList<>();
        for(WishList wishList : wishListList){
            Product product = productRepository.findProductById(wishList.getProduct().getId()).get();
            responseDtoList.add(toWishResponseDto(wishList, product));
        }
        return responseDtoList;
    }

    public void removeFromWishList(Long wishListId){
        wishListRepository.removeWishListById(wishListId);
    }

    public List<WishResponseDto> changeQuantity(Member member, Long wishListId, int amount){
        Optional<WishList> optionalWishList = wishListRepository.findWishListById(wishListId);
        if(optionalWishList.isEmpty()){
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        WishList wishList = optionalWishList.get();
        wishList.updateQuantity(amount);
        if(wishList.getQuantity() == 0){
            removeFromWishList(wishListId);
            return getList(member);
        }
        wishListRepository.save(wishList);
        return getList(member);
    }

}
