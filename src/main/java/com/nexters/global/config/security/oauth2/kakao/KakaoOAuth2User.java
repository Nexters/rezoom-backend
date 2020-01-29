package com.nexters.global.config.security.oauth2.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-12-19
 * Github : http://github.com/momentjin
 */
@Getter
public class KakaoOAuth2User implements OAuth2User {

    private String id;

    @JsonProperty(value = "kakao_account")
    private Map<String, ?> kakaoAccount;

    public KakaoOAuth2User() {

    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.singletonMap("id", id);
    }

    @Override
    public String getName() {
        if (!kakaoAccount.containsKey("profile"))
            return null;

        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        return profile.getOrDefault("nickname", "");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
