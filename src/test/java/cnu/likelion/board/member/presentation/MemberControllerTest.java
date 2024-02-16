package cnu.likelion.board.member.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;

import cnu.likelion.board.ApiTest;
import cnu.likelion.board.common.exception.ExceptionResponse;
import cnu.likelion.board.member.domain.MemberRepository;
import cnu.likelion.board.member.presentation.request.LoginRequest;
import cnu.likelion.board.member.presentation.request.MemberSignupRequest;
import cnu.likelion.board.member.presentation.response.LoginResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("회원 API 테스트")
class MemberControllerTest extends ApiTest {

    @Nested
    class 회원가입_시 {

        @Test
        void 성공하면_201_상태코드와_함께_응답_헤더의_Location_값으로_생성된_회원을_조회할_수_있는_URL이_반환된다() {
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
        }

        @Test
        void 아이디가_중복되면_409_상태코드와_예외_메세지를_반환한다() {
            // given
            MemberSignupRequest request = new MemberSignupRequest(
                    "likelion",
                    "likelion1234",
                    "멋사"
            );
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .extract();

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        }
    }

    @Nested
    class 로그인_시 {

        @BeforeEach
        void setUp() {
            MemberSignupRequest request = new MemberSignupRequest(
                    "likelion",
                    "likelion1234",
                    "멋사"
            );
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .extract();
        }

        @Test
        void 아이디가_없다면_401_상태코드와_예외_메세지를_보낸다() {
            // given
            LoginRequest request = new LoginRequest(
                    "none",
                    "likelion1234"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members/login")
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("존재하지 않는 아이디입니다.");
        }

        @Test
        void 비밀번호가_틀렸다면_401_상태코드와_예외_메세지를_보낸다() {
            // given
            LoginRequest request = new LoginRequest(
                    "likelion",
                    "wrong"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members/login")
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("비밀번호가 일치하지 않습니다.");
        }

        @Test
        void 로그인에_성공하면_200_상태코드와_AccessToken을_반환한다() {
            // given
            LoginRequest request = new LoginRequest(
                    "likelion",
                    "likelion1234"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members/login")
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.as(LoginResponse.class).accessToken())
                    .isNotNull();
        }
    }
}
