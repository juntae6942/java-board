package cnu.likelion.board.post.presentation;

import cnu.likelion.board.post.application.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // TODO [4단계] PostControllerTest를 참고하여 포스트 작성 API를 적절히 설계하시오
    public ResponseEntity<Void> write(
            // TODO [4단계] 3단계에서 만든 Auth 를 사용해 회원 정보를 받아옵니다.
            // TODO [4단계] 요청 본문에 포함된 데이터를 적절한 Request dto로 매핑합니다.
    ) {
        // TODO [4단계] 포스트를 생성하고, 생성된 아이디를 Location 헤더에 /posts/{postId} 값으로 넣어주세요
        return null;
    }

    // TODO [4단계] PostControllerTest를 참고하여 포스트 수정 API를 적절히 설계하시오
    public void update(
    ) {
    }

    // TODO [4단계] PostControllerTest를 참고하여 포스트 제거 API를 적절히 설계하시오
    public void delete(
    ) {
    }
}
