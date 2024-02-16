package cnu.likelion.board.post.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import cnu.likelion.board.ApiTest;
import cnu.likelion.board.auth.jwt.JwtService;
import cnu.likelion.board.common.exception.ExceptionResponse;
import cnu.likelion.board.member.domain.Member;
import cnu.likelion.board.post.presentation.request.PostUpdateRequest;
import cnu.likelion.board.post.presentation.request.PostWriteRequest;
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

@DisplayName("포스트 API 테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostControllerTest extends ApiTest {

    @Autowired
    private JwtService jwtService;

    private String 말랑_토큰;
    private String 동훈_토큰;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        // 회원가입
        Member 말랑 = memberRepository.save(new Member("mallang", "1234", "말랑"));
        Member 동훈 = memberRepository.save(new Member("dong", "1234", "동훈"));
        말랑_토큰 = jwtService.createToken(말랑.getId());
        동훈_토큰 = jwtService.createToken(동훈.getId());
    }

    @Nested
    class 포스트_생성_시 {

        @Test
        void 인증정보가_없으면_401_상태코드와_예외_메세지를_반환한다() {
            // given
            PostWriteRequest request = new PostWriteRequest(
                    "title",
                    "content"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/posts")
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("로그인 후 접근할 수 있습니다.");
        }

        @Test
        void 성공_시_201_상태코드와_포스트_조회_가능_url을_Location_해더에_포함하며_응답한다() {
            // given
            PostWriteRequest request = new PostWriteRequest(
                    "title",
                    "content"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 동훈_토큰)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/posts")
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location"))
                    .contains("/posts/");
        }
    }

    @Nested
    class 포스트_수정_시 {

        Long 동훈_포스트_ID;

        @BeforeEach
        void setUp() {
            PostWriteRequest request = new PostWriteRequest(
                    "title",
                    "content"
            );
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 동훈_토큰)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/posts")
                    .then()
                    .extract();
            동훈_포스트_ID = ID를_추출한다(response);
        }

        @Test
        void 인증정보가_없으면_401_상태코드와_예외_메세지를_반환한다() {
            // given
            PostUpdateRequest request = new PostUpdateRequest(
                    "u t",
                    "u c"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .put("/posts/{id}", 동훈_포스트_ID)
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("로그인 후 접근할 수 있습니다.");
        }

        @Test
        void 작성자가_아니면_403_상태코드와_예외_메세지를_반환한다() {
            // given
            PostUpdateRequest request = new PostUpdateRequest(
                    "u t",
                    "u c"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 말랑_토큰)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .put("/posts/{id}", 동훈_포스트_ID)
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("포스트에 대한 권한이 없습니다.");
        }

        @Test
        void 수정에_성공하면_200_상태코드를_반환한다() {
            // given
            PostUpdateRequest request = new PostUpdateRequest(
                    "u t",
                    "u c"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 동훈_토큰)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .put("/posts/{id}", 동훈_포스트_ID)
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }

    @Nested
    class 포스트_제거_시 {

        Long 동훈_포스트_ID;

        @BeforeEach
        void setUp() {
            PostWriteRequest request = new PostWriteRequest(
                    "title",
                    "content"
            );
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 동훈_토큰)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/posts")
                    .then()
                    .extract();
            동훈_포스트_ID = ID를_추출한다(response);
        }

        @Test
        void 인증정보가_없으면_401_상태코드와_예외_메세지를_반환한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .delete("/posts/{id}", 동훈_포스트_ID)
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("로그인 후 접근할 수 있습니다.");
        }

        @Test
        void 작성자가_아니면_403_상태코드와_예외_메세지를_반환한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 말랑_토큰)
                    .delete("/posts/{id}", 동훈_포스트_ID)
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
            assertThat(response.as(ExceptionResponse.class).message())
                    .isEqualTo("포스트에 대한 권한이 없습니다.");
        }

        @Test
        void 제거에_성공하면_200_상태코드를_반환한다() {
            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .header("Authorization", "Bearer " + 동훈_토큰)
                    .contentType(ContentType.JSON)
                    .delete("/posts/{id}", 동훈_포스트_ID)
                    .then()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }
    }
}
