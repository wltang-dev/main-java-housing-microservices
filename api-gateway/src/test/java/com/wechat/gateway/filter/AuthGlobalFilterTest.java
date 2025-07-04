package com.wechat.gateway.filter;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthGlobalFilterTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();

        configureFor("localhost", 8081);

        stubFor(post(urlEqualTo("/api/auth/login")).willReturn(ok()));
        stubFor(post(urlEqualTo("/api/auth/register")).willReturn(ok()));
        stubFor(get(urlEqualTo("/api/house/list")).willReturn(unauthorized()));
        stubFor(get(urlEqualTo("/api/house/list"))
                .withHeader(AUTHORIZATION, equalTo("Bearer faketoken123"))
                .willReturn(ok()));
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.gateway.discovery.locator.enabled", () -> false);
        registry.add("spring.cloud.gateway.routes[0].id", () -> "auth-service");
        registry.add("spring.cloud.gateway.routes[0].uri", () -> "http://localhost:8081");
        registry.add("spring.cloud.gateway.routes[0].predicates[0]", () -> "Path=/api/auth/**");

        registry.add("spring.cloud.gateway.routes[1].id", () -> "house-service");
        registry.add("spring.cloud.gateway.routes[1].uri", () -> "http://localhost:8081");
        registry.add("spring.cloud.gateway.routes[1].predicates[0]", () -> "Path=/api/house/**");
    }

    @Test
    void shouldAllowLoginAndRegisterWithoutToken() {
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/auth/login")
                .bodyValue("{\"username\":\"test\",\"password\":\"123\"}")
                .exchange()
                .expectStatus().isOk();

        webTestClient.post()
                .uri("http://localhost:" + port + "/api/auth/register")
                .bodyValue("{\"username\":\"new\",\"password\":\"abc\"}")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldBlockUnauthorized() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/house/list")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void shouldPassWithToken() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/house/list")
                .header(AUTHORIZATION, "Bearer faketoken123")
                .exchange()
                .expectStatus().isOk();
    }
}
