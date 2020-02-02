package com.nexters.rezoom.notification.application;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.OAuth2Member;
import com.nexters.rezoom.notification.domain.NotificationMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-12-20
 * Github : http://github.com/momentjin
 */
public class KakaoNotifier implements Notifier {

    private final static String API_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    private RestTemplate restTemplate;

    public KakaoNotifier() {
        this.restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(),
                new MappingJackson2HttpMessageConverter())
        );
    }

    @Override
    public void notifyToClient(Member member, NotificationMessage message) {
        final OAuth2Member oauth2Member = (OAuth2Member) member;
        final String accessToken = oauth2Member.getAccessToken();

        MultiValueMap<String, String> paramMap = getParams(message);
        callAPI(accessToken, paramMap);
    }

    private MultiValueMap<String, String> getParams(NotificationMessage message) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.put("template_object", Collections.singletonList(this.getTemplate(message)));
        return paramMap;
    }

    private String getTemplate(NotificationMessage notificationMessage) {
        String messageTemplate = "{\n" +
                "        \"object_type\": \"text\",\n" +
                "        \"text\": \"[contents]\",\n" +
                "        \"link\": {\n" +
                "            \"web_url\": \"[link]\",\n" +
                "            \"mobile_web_url\": \"[link]\"\n" +
                "        },\n" +
                "        \"button_title\": \"바로 확인\"\n" +
                "    }";

        messageTemplate = messageTemplate.replace("[contents]", notificationMessage.getContents());

        // TODO : 프로퍼티로 설정할 수 있게 만들자..
        // TODO : link가 작동하지 않는다. 뭔짓을해도 localhost:8080으로 접속된다. 도대체 왜!!!@!@!@!
        messageTemplate = messageTemplate.replace("[link]", "http://15.164.44.50:8081/");
        return messageTemplate;
    }

    private void callAPI(String accessToken, MultiValueMap paramMap) {
        RequestEntity<Map> requestEntity = RequestEntity.post(getURI())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(paramMap);

        ResponseEntity<HashMap> response = restTemplate.exchange(requestEntity, HashMap.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("자기소개서 마감 카카오톡 알림 실패");
        }
    }

    private URI getURI() {
        try {
            return new URI(API_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
