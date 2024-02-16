package cnu.likelion.board.member.domain;

import cnu.likelion.board.common.exception.UnAuthorizedException;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String username;
    private String password;
    private String name;

    public Member(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void signup(MemberValidator validator) {
        // TODO [1단계] validator를 통해 닉네임 중복 검증을 진행하세요
    }

    public void login(String password) {
        // TODO [2단계] 비밀번호가 일치하지 않으면 예외를 발생시킵니다.
        // TODO [2단계] 발생하는 예외는 테스트를 참고합니다.
    }
}
