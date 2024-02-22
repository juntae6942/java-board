package cnu.likelion.board.post.presentation;

import cnu.likelion.board.auth.Auth;
import cnu.likelion.board.post.application.PostService;
import cnu.likelion.board.post.presentation.request.PostUpdateRequest;
import cnu.likelion.board.post.presentation.request.PostWriteRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // TODO [4단계] PostControllerTest를 참고하여 포스트 작성 API를 적절히 설계하시오
    @PostMapping
    public ResponseEntity<Void> write(
            // TODO [4단계] 3단계에서 만든 Auth 를 사용해 회원 정보를 받아옵니다.
            // TODO [4단계] 요청 본문에 포함된 데이터를 적절한 Request dto로 매핑합니다.
            @Auth Long memberId,
            @RequestBody PostWriteRequest request
    ) {
        // TODO [4단계] 포스트를 생성하고, 생성된 아이디를 Location 헤더에 /posts/{postId} 값으로 넣어주세요
        log.error("memberId = {}", memberId);
        Long postId = postService.write(memberId, request.title(), request.content());
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

    // TODO [4단계] PostControllerTest를 참고하여 포스트 수정 API를 적절히 설계하시오
    @PutMapping("/{postId}")
    public void update(
            @Auth Long memberId,
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        postService.update(memberId, postId, request.title(), request.content());
    }

    // TODO [4단계] PostControllerTest를 참고하여 포스트 제거 API를 적절히 설계하시오
    @DeleteMapping("/{id}")
    public void delete(
            @Auth Long memberId,
            @PathVariable Long id
    ) {
        postService.delete(memberId, id);
    }
}
