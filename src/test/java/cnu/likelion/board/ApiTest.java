package cnu.likelion.board;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import cnu.likelion.board.member.domain.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class ApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected MemberRepository memberRepository;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
        memberRepository.clear();
    }

    public static Long ID를_추출한다(
            ExtractableResponse<Response> 응답
    ) {
        String location = 응답.header("Location");
        return Long.valueOf(location.substring(location.lastIndexOf("/") + 1));
    }
}
