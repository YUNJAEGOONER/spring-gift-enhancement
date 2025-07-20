package gift.Wishlist.service;

import gift.Wishlist.dto.WishRequestDto;
import gift.Wishlist.dto.WishResponseDto;
import gift.Wishlist.repository.WishListRepository;
import gift.member.Member;
import gift.product.Product;
import gift.Wishlist.WishList;
import gift.exception.ErrorCode;
import gift.member.exception.MemberNotFoundException;
import gift.product.exception.ProductNotFoundException;
import gift.Wishlist.exception.WishNotFoundException;
import gift.member.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<WishResponseDto> getList(Long memberId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<WishList> wishListList = wishListRepository.findWishListByMemberId(pageable, memberId);
        return wishListList.map(
                wishList -> new WishResponseDto(
                        wishList.getId(),
                        wishList.getProduct().getName(),
                        wishList.getProduct().getImageUrl(),
                        wishList.getQuantity(),
                        wishList.getQuantity() * wishList.getProduct().getPrice())
        );
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
