package cnu.likelion.board.auth;

import static java.util.Objects.requireNonNull;

import cnu.likelion.board.auth.jwt.JwtService;
import cnu.likelion.board.common.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // TODO [3단계] parameter가 Auth 애노테이션을 달고 있으며, Long 타입의 파라미터인 경우에 지원한다
        return parameter.hasParameterAnnotation(Auth.class) && parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Auth authAt = parameter.getParameterAnnotation(Auth.class);  // TODO [3단계] parameter 에 달린 Auth 어노테이션을 가져온다.
        requireNonNull(authAt);
        String accessToken = extractAccessToken(webRequest);
        // TODO [3단계] JwtService를 사용하여 액세스 토큰에서 회원 ID를 추출하여 반환한다.
        return jwtService.extractMemberId(accessToken);
    }

    private static String extractAccessToken(NativeWebRequest request) {
        // TODO [3단계] webRequest는 요청 정보를 담고있다. Authorization 헤더에 설정된 값을 가져오자.
        String bearerToken = request.getHeader("Authorization");

        // TODO [3단계] 가져온 값은 [Bearer accessToken] 형식이어야 한다. 해당 값이 존재하고, Bearer 로 시작하는지 확인한 뒤,
        if (bearerToken.startsWith("Bearer ")) {
            // TODO [3단계] 올바르다면 [Bearer ] 을 제외한 부분만을 가져온다. 해당 부분이 AccessToken이 되며 이를 반환하자.
            bearerToken = bearerToken.substring(7);
            log.info("bearerToken = {}", bearerToken);
            return bearerToken;
        }

        // TODO [3단계] 만약 헤더 값이 없거나, [Bearer accessToken] 형식이 아닌 경우 아래 예외가 발생된다.
        throw new UnAuthorizedException("로그인 후 접근할 수 있습니다.");
    }
}
