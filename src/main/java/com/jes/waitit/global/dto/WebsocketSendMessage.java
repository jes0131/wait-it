package com.jes.waitit.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebsocketSendMessage<T> {
    private String event;
    private T data;

    public static <T>  WebsocketSendMessage<T> build(String event, T data) {
        return WebsocketSendMessage.<T>builder().event(event).data(data).build();
    }
}