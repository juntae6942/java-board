package cnu.likelion.board.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cnu.likelion.board.common.exception.ConflictException;
import cnu.likelion.board.common.exception.NotFoundException;
import cnu.likelion.board.common.exception.UnAuthorizedException;
import cnu.likelion.board.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("회원 서비스 (MemberService) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.clear();
    }

    @Nested
    class 회원가입_시 {

        @Test
        void 진행하고_생성된_회원의_id를_반환한다() {
            // when
            Long id = memberService.signup(
                    "username",
                    "password",
                    "회원"
            );

            // then
            assertThat(id).isNotNull();
        }

        @Test
        void 아이디가_중복되면_예외() {
            // given
            memberService.signup(
                    "username",
                    "password",
                    "회원"
            );

            // when & then
            assertThatThrownBy(() -> {
                memberService.signup(
                        "username",
                        "password",
                        "회원"
                );
            }).isInstanceOf(ConflictException.class)
                    .hasMessage("이미 존재하는 아이디입니다.");
        }
    }

    @Nested
    class 로그인_시 {

        @Test
        void 없는_아이디라면_예외() {
            // given
            memberService.signup(
                    "username",
                    "password",
                    "name"
            );

            // when & then
            assertThatThrownBy(() -> {
                memberService.login("none", "none");
            }).isInstanceOf(UnAuthorizedException.class)
                    .hasMessage("존재하지 않는 아이디입니다.");
        }

        @Test
        void 비밀번호가_일치하지_않으면_예외() {
            // given
            memberService.signup(
                    "username",
                    "password",
                    "name"
            );

            // when & then
            assertThatThrownBy(() -> {
                memberService.login("username", "none");
            }).isInstanceOf(UnAuthorizedException.class)
                    .hasMessage("비밀번호가 일치하지 않습니다.");
        }

        @Test
        void 성공_시_회원_ID_반환() {
            // given
            Long memberId = memberService.signup(
                    "username",
                    "password",
                    "name"
            );

            // when
            Long loginId = memberService.login("username", "password");

            // then
            assertThat(loginId).isEqualTo(memberId);
        }
    }
}
