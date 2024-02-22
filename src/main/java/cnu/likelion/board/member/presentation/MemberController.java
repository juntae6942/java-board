package cnu.likelion.board.member.presentation;

import cnu.likelion.board.auth.jwt.JwtService;
import cnu.likelion.board.member.application.MemberService;
import cnu.likelion.board.member.domain.MemberRepository;
import cnu.likelion.board.member.presentation.request.LoginRequest;
import cnu.likelion.board.member.presentation.request.MemberSignupRequest;
import cnu.likelion.board.member.presentation.response.LoginResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
// TODO [1단계] 회원 컨드롤러를 RestController 빈으로 등록하세요.
// TODO [1단계] /members 로 시작되는 URI에 대해 매핑되도록 합니다.
@RestController
@RequestMapping("/members")
public class MemberController {

    // TODO [1단계] MemberService 를 의존성 주입 받으세요.
    private final MemberService memberService;
    // TODO [2단계] JwtService 를 의존성 주입 받으세요.
    private final JwtService jwtService;
    // TODO [1단계] [ POST , /members ] 로 들어오는 요청에 대해 동작해야 합니다.
    @PostMapping
    public ResponseEntity<Void> signup(
            // TODO [1단계] Json 타입으로 들어오는 request Body를 매핑합니다.
            @RequestBody MemberSignupRequest request
    ) {
        // TODO [1단계] 회원 서비스를 사용하여 회원가입을 진행하세요
        // TODO [1단계] ResponseEntity.create(URI.create())를 활용하여,
        // TODO [1단계] 응답 헤더 중 Location에 생성된 회원을 조회할 수 있는 URI인 /members/{생성된 Id} 를 반환합니다.
        Long memberId = memberService.signup(request.username(), request.password(), request.name());
        return ResponseEntity.created(URI.create("/members/"+memberId)).build();
    }

    // TODO [2단계] [ POST , /members/login ] 로 들어오는 요청에 대해 동작해야 합니다.
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            // TODO [2단계] Json 타입으로 들어오는 request Body를 매핑합니다.
            @RequestBody LoginRequest request
    ) {
        // TODO [2단계] 회원 서비스를 사용하여 로그인을 진행합니다.
        // TODO [2단계] 로그인 결과 반환된 Id를 가지고 Jwt(AccessToken)를 생성합니다.
        // TODO [2단계] 생성한 accessToken을 LoginResponse로 감싸 반환합니다.
        Long memberId = memberService.login(request.username(), request.password());
        String token = jwtService.createToken(memberId);
        LoginResponse loginResponse = new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }
}
