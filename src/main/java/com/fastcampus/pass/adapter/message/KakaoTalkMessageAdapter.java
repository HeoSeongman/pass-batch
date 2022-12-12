package com.fastcampus.pass.adapter.message;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoTalkMessageAdapter {
    private final WebClient client;

    public KakaoTalkMessageAdapter(KakaoTalkMessageConfig config) {
        client = WebClient.builder()
                .baseUrl(config.getHost())
                .defaultHeaders(h -> {
                    h.setBearerAuth(config.getToken());
                    h.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                })
                .build();
    }

    public boolean sendKakaoTalkMessage(final String uuid, final String text) {
        KakaoTalkMessageResponse response = client.post().uri("/v1/api/talk/friends/message/default/send")
                .body(BodyInserters.fromValue(KakaoTalkMessageRequest.class))
                .retrieve()
                .bodyToMono(KakaoTalkMessageResponse.class)
                .block();

        if (response == null || response.getSuccessful_receiver_uuids() == null) {
            return false;
        }

        return response.getSuccessful_receiver_uuids().size() > 0;
    }
}
