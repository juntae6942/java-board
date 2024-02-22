package cnu.likelion.board.post.application;

import cnu.likelion.board.common.exception.ForbiddenException;
import cnu.likelion.board.common.exception.UnAuthorizedException;
import cnu.likelion.board.member.domain.Member;
import cnu.likelion.board.member.domain.MemberRepository;
import cnu.likelion.board.post.domain.Post;
import cnu.likelion.board.post.domain.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long write(
            Long memberId,
            String title,
            String content
    ) {
        // TODO [4단계] 포스트를 작성하는 로직을 작성하시오. 생성된 포스트 Id를 반환합니다.
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UnAuthorizedException("로그인 후 접근할 수 있습니다."));
        Post post = new Post(title, content, member);
            return postRepository.save(post).getId();
    }

    public void update(
            Long memberId,
            Long postId,
            String title,
            String content
    ) {
        // TODO [4단계] 포스트 수정 로직을 작성하시오.
        // TODO [4단계] 수정 전 포스트에 대한 접근 권한을 확인해야 합니다.
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UnAuthorizedException("회원 정보가 존재하지 않습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new UnAuthorizedException("포스트 정보가 존재하지 않습니다."));
        post.validateAuthority(member);
        post.update(title, content);
    }

    public void delete(
            Long memberId,
            Long postId
    ) {
        // TODO [4단계] 포스트 삭제 로직을 작성하시오.
        // TODO [4단계] 삭제 전 포스트에 대한 접근 권한을 확인해야 합니다.
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new UnAuthorizedException("포스트에 대한 권한이 없습니다."));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new UnAuthorizedException("포스트에 대한 권한이 없습니다.") );
        post.validateAuthority(member);
        postRepository.delete(post);
    }
}
