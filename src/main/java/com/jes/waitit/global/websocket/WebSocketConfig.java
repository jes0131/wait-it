package com.jes.waitit.global.websocket;

import com.jes.waitit.domain.reservation.websocket.ReservationValidationInterceptor;
import com.jes.waitit.domain.reservation.websocket.ReservationStatusHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ReservationStatusHandler reservationStatusHandler;

    private final ReservationValidationInterceptor reservationValidationInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(reservationStatusHandler, "/ws/reservation/status")
                .addInterceptors(reservationValidationInterceptor)
                .setAllowedOrigins("*");
    }
}