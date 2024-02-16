package cnu.likelion.board.post.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cnu.likelion.board.common.exception.ForbiddenException;
import cnu.likelion.board.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("게시글 (Post) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostTest {

    Member 동훈;
    Member 말랑;

    @BeforeEach
    void setUp() {
        동훈 = new Member("username", "1234", "동훈");
        말랑 = new Member("mallang", "1234", "말랑");
        ReflectionTestUtils.setField(동훈, "id", 1L);
        ReflectionTestUtils.setField(말랑, "id", 2L);
    }

    @Test
    void 게시글에_대한_권한_검증시_작성자는_권한이_있다() {
        // given
        Post post = new Post("제목", "내용", 동훈);

        // when & then
        assertDoesNotThrow(() -> {
            post.validateAuthority(동훈);
        });
    }

    @Test
    void 게시글에_대한_권한_검증_시_작성자가_아니면_권한이_없다() {
        // given
        Post post = new Post("제목", "내용", 동훈);

        // when & then
        assertThatThrownBy(() -> {
            post.validateAuthority(말랑);
        }).isInstanceOf(ForbiddenException.class)
                .hasMessage("포스트에 대한 권한이 없습니다.");
    }

    @Test
    void 게시글을_수정할_수_있다() {
        // given
        Post post = new Post("제목", "내용", 동훈);

        // when
        post.update("업데이트 제목", "업데이트 내용");

        // then
        assertThat(post.getTitle()).isEqualTo("업데이트 제목");
        assertThat(post.getContent()).isEqualTo("업데이트 내용");
    }
}
