package cnu.likelion.board.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import cnu.likelion.board.ApiTest;
import cnu.likelion.board.member.request.MemberSignupRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("회원 API 테스트")
class MemberControllerTest extends ApiTest {

    @Nested
    class 회원가입_시 {

        @Test
        void 회원가입에_성공하면_201_상태코드와_함께_응답_헤더의_Location_값으로_생성된_회원을_조회할_수_있는_URL이_반환된다() {
            // given
            MemberSignupRequest request = new MemberSignupRequest(
                    "likelion",
                    "likelion1234",
                    "멋사"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).contains("/members/");
            System.out.println(response.header("Location"));
        }
    }
}
