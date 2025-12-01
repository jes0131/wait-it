package com.jes.waitit.domain.reservation.websocket.handler;

import com.jes.waitit.domain.reservation.service.ReservationService;
import com.jes.waitit.domain.reservation.websocket.broadcaster.ReservationStatusBroadcaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationStatusHandler extends TextWebSocketHandler {
    private final ReservationService reservationService;
    private final ReservationStatusBroadcaster reservationStatusBroadcaster;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long reservationId = (Long) session.getAttributes().get("id");

        reservationStatusBroadcaster.addSession(reservationId, session);
        log.info("[{}] Connection Established to {}", this.getClass().getSimpleName(), reservationId);
        reservationStatusBroadcaster.sendInitialData(reservationId, session, reservationService.getInitialData(reservationId));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long reservationId = (Long) session.getAttributes().get("reservationId");
        reservationStatusBroadcaster.removeSession(reservationId, session);
    }
}