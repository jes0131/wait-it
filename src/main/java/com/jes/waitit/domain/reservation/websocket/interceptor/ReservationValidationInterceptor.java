package com.jes.waitit.domain.reservation.websocket.interceptor;

import com.jes.waitit.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationValidationInterceptor implements HandshakeInterceptor {
    private final ReservationRepository reservationRepository;

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler handler,
            Map<String, Object> attributes
    ) throws Exception {
        String idParam = UriComponentsBuilder
                .fromUri(request.getURI())
                .build()
                .getQueryParams()
                .getFirst("id");

        if (idParam == null || idParam.isBlank()) {
            log.warn("[{}] query id is null", this.getClass().getSimpleName());
            return false;
        }

        Long reservationId;
        try {
            reservationId = Long.valueOf(idParam);
        } catch (NumberFormatException e) {
            log.warn("[{}] query id format error", this.getClass().getSimpleName());
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return false;
        }

        if (!reservationRepository.existsByIdAndIsDeletedFalse(reservationId)) {
            log.warn("[{}] Reservation {} not found", this.getClass().getSimpleName(), reservationId);
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return false;
        }

        attributes.put("id", reservationId);
        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception ex) {
    }
}
