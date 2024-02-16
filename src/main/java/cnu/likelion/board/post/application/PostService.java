package cnu.likelion.board.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    public Long write(
            Long memberId,
            String title,
            String content
    ) {
        // TODO [4단계] 포스트를 작성하는 로직을 작성하시오. 생성된 포스트 Id를 반환합니다.
        return null;
    }

    public void update(
            Long memberId,
            Long postId,
            String title,
            String content
    ) {
        // TODO [4단계] 포스트 수정 로직을 작성하시오.
        // TODO [4단계] 수정 전 포스트에 대한 접근 권한을 확인해야 합니다.
    }

    public void delete(
            Long memberId,
            Long postId
    ) {
        // TODO [4단계] 포스트 삭제 로직을 작성하시오.
        // TODO [4단계] 삭제 전 포스트에 대한 접근 권한을 확인해야 합니다.
    }
}
