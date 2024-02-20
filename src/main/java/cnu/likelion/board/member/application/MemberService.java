package cnu.likelion.board.member.application;

import cnu.likelion.board.member.domain.Member;
import cnu.likelion.board.member.domain.MemberRepository;
import cnu.likelion.board.member.domain.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
// TODO [1단계] Service 빈으로 등록하세요
@Service
public class MemberService {
    // TODO [1단계] MemberRepository와, MemberValidator를 의존성 주입 받습니다.
    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    public Long signup(
            String username,
            String password,
            String name
    ) {
        // TODO [1단계] 회원 객체를 만들고, 회원가입을 진행하세요.
        // TODO [1단계] 회원가입 시 아이디 중복 체크는 이곳에서 하지 말고, 회원의 메서드를 통해 진행되도록 합니다.
        // TODO [1단계] 중복 체크 이후 회원 정보를 저장하고, 저장된 회원의 id를 반환합니다.
        Member member = new Member(username, password, name);
        member.signup(memberValidator);
        return memberRepository.save(member).getId();
    }
}
