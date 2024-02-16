package cnu.likelion.board.member.application;

import cnu.likelion.board.member.domain.Member;
import cnu.likelion.board.member.domain.MemberRepository;
import cnu.likelion.board.member.domain.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// TODO Service 빈으로 등록하세요
public class MemberService {

    // TODO MemberRepository와, MemberValidator를 의존성 주입 받습니다.

    public Long signup(
            String username,
            String password,
            String name
    ) {
        // TODO 회원 객체를 만들고, 회원가입을 진행하세요.
        // TODO 회원가입 시 아이디 중복 체크는 이곳에서 하지 말고, 회원의 메서드를 통해 진행되도록 합니다.
        // TODO 중복 체크 이후 회원 정보를 저장하고, 저장된 회원의 id를 반환합니다.
        return null;
    }
}
