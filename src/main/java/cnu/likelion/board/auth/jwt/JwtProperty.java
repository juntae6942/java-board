package cnu.likelion.board.auth.jwt;

// TODO [2단계] application.yml 파일에서 jwt 로 시작되는 값을 가져옵니다. 이를 위해 @ConfigurationProperties 을 사용하세요.
// TODO [2단계] @ConfigurationProperties 를 위해서는 BoardApplication 클래스에 @ConfigurationPropertiesScan 를 선언해주어야 합니다.
public record JwtProperty(
        String secretKey,
        Long accessTokenExpirationDay
) {
}
