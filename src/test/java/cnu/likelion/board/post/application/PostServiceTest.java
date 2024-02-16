package cnu.likelion.board.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cnu.likelion.board.common.exception.ForbiddenException;
import cnu.likelion.board.member.application.MemberService;
import cnu.likelion.board.member.domain.MemberRepository;
import cnu.likelion.board.post.domain.Post;
import cnu.likelion.board.post.domain.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("포스트 서비스 (PostService) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Long 동훈_Id;
    private Long 말랑_Id;

    @BeforeEach
    void setUp() {
        memberRepository.clear();
        postRepository.clear();
        동훈_Id = memberService.signup("dong", "1234", "동훈");
        말랑_Id = memberService.signup("mallang", "1234", "말랑");
    }

    @Test
    void 게시글을_작성한다() {
        // when
        Long postId = postService.write(동훈_Id, "제목", "내용");

        // then
        assertThat(postId).isNotNull();
    }

    @Test
    void 게시글을_수정한다() {
        // given
        Long postId = postService.write(동훈_Id, "제목", "내용");

        // when
        postService.update(동훈_Id, postId, "업뎃 제목", "업뎃 내용");

        // then
        Post post = postRepository.findById(postId).get();
        assertThat(post.getTitle()).isEqualTo("업뎃 제목");
        assertThat(post.getContent()).isEqualTo("업뎃 내용");
    }

    @Test
    void 게시글_수정_시_작성자가_아니면_예외() {
        // given
        Long postId = postService.write(동훈_Id, "제목", "내용");

        // when & then
        assertThatThrownBy(() -> {
            postService.update(말랑_Id, postId, "업뎃 제목", "업뎃 내용");
        }).isInstanceOf(ForbiddenException.class)
                .hasMessage("포스트에 대한 권한이 없습니다.");
    }

    @Test
    void 게시글을_삭제한다() {
        // given
        Long postId = postService.write(동훈_Id, "제목", "내용");

        // when
        postService.delete(동훈_Id, postId);

        // then
        assertThat(postRepository.findById(postId)).isEmpty();
    }

    @Test
    void 게시글_삭제_시_작성자가_아니면_예외() {
        // given
        Long postId = postService.write(동훈_Id, "제목", "내용");

        // when & then
        assertThatThrownBy(() -> {
            postService.delete(말랑_Id, postId);
        }).isInstanceOf(ForbiddenException.class)
                .hasMessage("포스트에 대한 권한이 없습니다.");
    }
}
