package cnu.likelion.board.auth;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class AuthConfig implements WebMvcConfigurer {
    // TODO [3단계] AuthArgumentResolver 를 의존성 주입 받는다.
    private final AuthArgumentResolver authArgumentResolver;

    // TODO [3단계] ArgumentResolver를 등록해보자. WebMvcConfigurer의 메서드를 오버라이딩하여 등록할 수 있다.
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
