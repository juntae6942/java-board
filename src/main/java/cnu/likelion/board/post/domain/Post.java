package cnu.likelion.board.post.domain;

import cnu.likelion.board.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Post {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private Member writer;

    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void validateAuthority(Member member) {
        // TODO [4단계] 포스트 작성자와 주어진 회원이 동일한지 검사하고, 동일하지 않으면 예외를 발생시킵니다.
        // TODO [4단계] 회원은 Id가 같다면 필드가 달라도 동일한 회원이라 판단합니다.
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
