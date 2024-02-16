package cnu.likelion.board.member.domain;

import cnu.likelion.board.member.domain.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.springframework.stereotype.Repository;

// TODO Repository 빈으로 등록하세요.
public class MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 1L;

    public Member save(Member member) {
        // TODO member에 Id가 중복되지 않도록 세팅하고,
        // TODO members 에 id를 Key로, member를 value로 저장하세요.
        return null;
    }

    public Optional<Member> findById(Long id) {
        // TODO 저장된 회원 중 id가 일치하는 회원을 찾아 Optional 로 감싸서 반환하세요.
        // TODO 만약 id가 일치하는 회원이 없는 경우, 빈 Optional 객체를 반환하세요.
        return null;
    }

    public Optional<Member> findByUsername(String username) {
        // TODO 저장된 회원 중 username이 일치하는 회원을 찾아 Optional 로 감싸서 반환하세요.
        // TODO 만약 username이 일치하는 회원이 없는 경우, 빈 Optional 객체를 반환하세요.
        return null;
    }

    // for test
    public void clear() {
        this.members.clear();
    }
}
