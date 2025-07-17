package gift.service;

import gift.dto.wish.WishRequestDto;
import gift.dto.wish.WishResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.WishList;
import gift.exception.ErrorCode;
import gift.exception.member.MemberNotFoundException;
import gift.exception.product.ProductNotFoundException;
import gift.exception.wish.WishNotFoundException;
import gift.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    public WishResponseDto addToWishList(Long memberId, WishRequestDto requestDto) {
        Product product = productRepository.findProductById(requestDto.productId()).orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        Member member = memberRepository.findMemberById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        Optional<WishList> wishListOptional = wishListRepository.findWishListByMemberIdAndProductId(memberId, requestDto.productId());
        if (wishListOptional.isPresent()) {
            //이미 장바구니에 해당 상품이 있는 경우에는 수량만 업데이트
            WishList wishList = wishListOptional.get();
            wishList.updateQuantity(requestDto.quantity());
            return toWishResponseDto(wishList, product);
        }
        WishList wishList = wishListRepository.save(new WishList(member, product, requestDto.quantity()));
        return toWishResponseDto(wishList, product);
    }

    public WishResponseDto toWishResponseDto(WishList wishList, Product product){
        Integer totalPrice = wishList.getQuantity() * product.getPrice();
        return new WishResponseDto(wishList.getId(), product.getName(), product.getImageUrl(), wishList.getQuantity(), totalPrice);
    }

    public List<WishResponseDto> getList(Long memberId){
        List<WishList> wishListList = wishListRepository.findWishListByMemberId(memberId);
        List<WishResponseDto> responseDtoList = new ArrayList<>();
        for(WishList wishList : wishListList){
            responseDtoList.add(toWishResponseDto(wishList, wishList.getProduct()));
        }
        return responseDtoList;
    }

    public void removeFromWishList(Long wishListId){
        wishListRepository.removeWishListById(wishListId);
    }

    public WishResponseDto changeQuantity(Long wishListId, int amount){
        WishList wishList = wishListRepository.findWishListById(wishListId).orElseThrow(() -> new WishNotFoundException(ErrorCode.WISH_NOT_FOUND));
        wishList.updateQuantity(amount);
        if(wishList.getQuantity() == 0){
            removeFromWishList(wishListId);
            return toWishResponseDto(wishList, wishList.getProduct());
        }
        return toWishResponseDto(wishList, wishList.getProduct());
    }

}
