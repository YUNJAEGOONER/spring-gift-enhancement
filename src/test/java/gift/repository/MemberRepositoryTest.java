package gift.repository;


import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Member;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    void 회원_생성(){
        Member member = memberRepository.save(new Member("user1@kakao.com", "demopassword"));
        System.out.println("member = " + member);
        assertThat(member.getMemberId()).isNotNull();
        assertThat(member.getEmail()).isEqualTo("user1@kakao.com");
    }

    @Test
    void 아이디로_회원조회(){
        Member member = memberRepository.save(new Member("user2@pusan.ac.kr", "demopassword"));
        Optional<Member> findMember = memberRepository.findMemberByMemberId(member.getMemberId());
        System.out.println("findMember = " + findMember);
        assertThat(findMember).isNotNull();
        assertThat(findMember.get().getEmail()).isEqualTo(member.getEmail());
    }


    @Test
    void 이메일로_회원조회(){
        Member member = memberRepository.save(new Member("user3@naver.com", "demopassword"));
        Optional<Member> findMember = memberRepository.findMemberByEmail("user3@kakao.com");
        assertThat(findMember).isEmpty();
        findMember = memberRepository.findMemberByEmail("user3@naver.com");
        assertThat(findMember).isNotNull();
        assertThat(findMember.get().getMemberId()).isEqualTo(member.getMemberId());
    }

}