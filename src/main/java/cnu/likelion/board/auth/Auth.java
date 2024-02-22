package cnu.likelion.board.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO [3단계] 파라미터에 어노테이션을 붙일 수 있게 설정한다.
// TODO [3단계] 런타임에도 어노테이션 정보가 유지되도록 한다.
// TODO [3단계] 잘 모르겠다면 스프링의 어노테이션을 참고한다
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
}
