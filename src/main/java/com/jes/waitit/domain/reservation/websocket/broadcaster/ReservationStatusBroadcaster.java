package com.jes.waitit.domain.reservation.websocket.broadcaster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jes.waitit.domain.reservation.websocket.dto.ReservationStatusInitialDataDTO;
import com.jes.waitit.domain.reservation.websocket.dto.ReservationStatusUpdateLastProcessedWaitingNumDTO;
import com.jes.waitit.domain.reservation.websocket.dto.ReservationStatusUpdateWaitingCountDTO;
import com.jes.waitit.domain.reservation.enums.ReservationStatusEvent;
import com.jes.waitit.global.dto.WebsocketSendMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReservationStatusBroadcaster {
    private final ConcurrentHashMap<Long, Set<WebSocketSession>> clients = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public void addSession(Long reservationId, WebSocketSession session) {
        clients.computeIfAbsent(reservationId, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public void removeSession(Long reservationId, WebSocketSession session) {
        Set<WebSocketSession> sessions = clients.get(reservationId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                clients.remove(reservationId);
            }
        }
    }

    public void sendInitialData(Long reservationId, WebSocketSession session, ReservationStatusInitialDataDTO dto) {
        String message;
        try {
            message = mapper.writeValueAsString(
                    WebsocketSendMessage.build(
                            ReservationStatusEvent.INITIAL_DATA.toString(),
                            dto
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                this.removeSession(reservationId, session);
            }
        }
    }

    public void updateWaitingCount(Long reservationId, Integer waitingCount) {
        String message;
        try {
            message = mapper.writeValueAsString(
                    WebsocketSendMessage.build(
                            ReservationStatusEvent.UPDATE_WAITING_COUNT.toString(),
                            new ReservationStatusUpdateWaitingCountDTO(waitingCount)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        clients.getOrDefault(reservationId, ConcurrentHashMap.newKeySet()).forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    this.removeSession(reservationId, session);
                }
            }
        });
    }

    public void updateLastProcessedWaitingNum(Long reservationId, Integer lastProcessedWaitingNum) {
        String message;
        try {
            message = mapper.writeValueAsString(
                    WebsocketSendMessage.build(
                            ReservationStatusEvent.UPDATE_LAST_PROCESSED_WAITING_NUM.toString(),
                            new ReservationStatusUpdateLastProcessedWaitingNumDTO(lastProcessedWaitingNum)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        clients.getOrDefault(reservationId, ConcurrentHashMap.newKeySet()).forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    this.removeSession(reservationId, session);
                }
            }
        });
    }
}