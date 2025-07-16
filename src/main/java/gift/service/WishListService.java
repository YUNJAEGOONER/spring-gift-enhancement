package gift.service;

import gift.dto.wish.WishRequestDto;
import gift.dto.wish.WishResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.WishList;
import gift.exception.ErrorCode;
import gift.exception.member.MemberNotFoundException;
import gift.exception.product.ProductNotFoundException;
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

    //단일 WishResponseDto가
    public WishResponseDto addToWishList(Long memberId, WishRequestDto requestDto) {
        Optional<Product> optionalProduct = productRepository.findProductById(requestDto.productId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Optional<Member> optionalMember = memberRepository.findMemberById(memberId);
        if(optionalMember.isEmpty()){
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Optional<WishList> wishListOptional = wishListRepository.findWishListByMemberIdAndProductId(memberId, requestDto.productId());
        if (wishListOptional.isPresent()) {
            //이미 장바구니에 해당 상품이 있는 경우에는 수량만 업데이트
            return changeQuantity(wishListOptional.get().getId(), requestDto.quantity());
        }
        WishList wishList = wishListRepository.save(new WishList(optionalMember.get(), optionalProduct.get(), requestDto.quantity()));
        return toWishResponseDto(wishList, optionalProduct.get());
    }

    public WishResponseDto toWishResponseDto(WishList wishList, Product product){
        Integer totalPrice = wishList.getQuantity() * product.getPrice();
        return new WishResponseDto(wishList.getId(), product.getName(), product.getImageUrl(), wishList.getQuantity(), totalPrice);
    }

    public List<WishResponseDto> getList(Long memberId){
        List<WishList> wishListList = wishListRepository.findWishListByMemberId(memberId);
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

    public WishResponseDto changeQuantity(Long wishListId, int amount){
        Optional<WishList> optionalWishList = wishListRepository.findWishListById(wishListId);
        if(optionalWishList.isEmpty()) throw new RuntimeException();
        WishList wishList = optionalWishList.get();
        wishList.updateQuantity(amount);
        if(wishList.getQuantity() == 0){
            removeFromWishList(wishListId);
            return toWishResponseDto(wishList, wishList.getProduct());
        }
        wishListRepository.save(wishList);
        return toWishResponseDto(wishList, wishList.getProduct());
    }

}
