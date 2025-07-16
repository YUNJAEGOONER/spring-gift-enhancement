package gift.entity;

import gift.dto.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // 암호화된 형태로 저장하기

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; //회원 가입 -> 일반 회원

    @OneToMany(mappedBy = "member")
    private List<WishList> wishLists = new ArrayList<>();

    public void changeInfo(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Member(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Member() {};

    public Long getMemberId(){
        return id;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public Role getRole() {
        return role;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }
}