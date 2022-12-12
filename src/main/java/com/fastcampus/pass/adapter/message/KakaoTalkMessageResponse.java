package com.fastcampus.pass.adapter.message;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class KakaoTalkMessageResponse {
    private List<String> successful_receiver_uuids;
}
