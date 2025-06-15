package com.wechat.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 放行某些路径，比如登录、注册等不需要 token 的接口
        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            return chain.filter(exchange);
        }

        // 从请求头获取 token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        // 简单判断 token 是否存在（实际可以解析 JWT 等）
        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // TODO: 在此可以加 JWT 解析、过期校验、用户权限校验等

        return chain.filter(exchange); // 放行
    }

    @Override
    public int getOrder() {
        return -1; // 越小越优先执行
    }
}
