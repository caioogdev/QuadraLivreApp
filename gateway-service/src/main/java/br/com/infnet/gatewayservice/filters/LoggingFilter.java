package br.com.infnet.gatewayservice.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        String method = exchange.getRequest().getMethod().toString();

        logger.info("Requisição recebida: {} {}", method, path);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            String status = String.valueOf(exchange.getResponse().getStatusCode());
            logger.info("Resposta enviada: {} {} - status={}", method, path, status);
        }));
    }
}
