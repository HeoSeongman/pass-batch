package com.fastcampus.pass.adapter.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
public class KakaoTalkMessageRequest {

    private List<String> receiver_uuids;
    private TemplateObject template_object;


    @Getter
    @Setter
    @ToString
    public static class TemplateObject {
        private String object_type;
        private String text;
        private Link link;

        @Getter
        @Setter
        @ToString
        public static class Link {
            private String web_url;
        }


    }

    public KakaoTalkMessageRequest(String uuid, String text) {
        List<String> receiverUuids = Collections.singletonList(uuid);

        TemplateObject.Link link = new TemplateObject.Link();
        TemplateObject object = new TemplateObject();
        object.setObject_type("text");
        object.setText(text);
        object.setLink(link);

        this.receiver_uuids = receiverUuids;
        this.template_object = object;
    }
}
