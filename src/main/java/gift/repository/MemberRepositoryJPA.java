package gift.repository;

import gift.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryJPA extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmailAndPassword(String email, String password);
    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByMemberId(Long memberId);
    void removeMemberByMemberId(Long memberId);
}
