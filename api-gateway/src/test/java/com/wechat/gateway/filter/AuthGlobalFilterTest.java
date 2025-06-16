package com.wechat.gateway.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthGlobalFilterTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldAllowLoginAndRegisterWithoutToken() {
        // login
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/auth/login")
                .header("Content-Type", "application/json")
                .bodyValue("{\"username\": \"test\", \"password\": \"123456\"}")
                .exchange()
                .expectStatus().isOk();

        // register
        webTestClient.post()
                .uri("http://localhost:" + port + "/api/auth/register")
                .header("Content-Type", "application/json")
                .bodyValue("{\"username\": \"newuser\", \"password\": \"abc123\"}")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldBlockRequestWithoutToken() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/house/list")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void shouldBlockInvalidToken() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/house/list")
                .header(AUTHORIZATION, "Bearer invalid.token.value")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void shouldAllowBearerToken() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/api/house/list")
                .header(AUTHORIZATION, "Bearer faketoken123")
                .exchange()
                .expectStatus().value(status -> {
                    if (status == UNAUTHORIZED.value()) {
                        throw new AssertionError("Token 应该放行但被拦截了");
                    }
                });
    }
}
