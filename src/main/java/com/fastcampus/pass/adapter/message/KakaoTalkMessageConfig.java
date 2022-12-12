package com.fastcampus.pass.adapter.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kakaotalk")
public class KakaoTalkMessageConfig {
    private String host;
    private String token;
}
