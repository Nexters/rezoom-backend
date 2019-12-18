package com.nexters.config.security.oauth2;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedHashMap;

/**
 * Created by momentjin@gmail.com on 2019-12-11
 * Github : http://github.com/momentjin
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private MemberRepository memberRepository;

    @Autowired
    public MyOAuth2AuthorizedClientService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {

        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId(); // kakao
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken(); // { tokenValue, issuedAt, expiresAt }

        String id = authentication.getName();

        LinkedHashMap<String, Object> properties = (LinkedHashMap<String, Object>) ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes().get("properties");
        String name = (String) properties.get("nickname");

        Member member = Member.builder()
                .id(id)
                .name(name)
                .providerType(providerType)
                .accessToken(accessToken.getTokenValue())
                .expiresAt(LocalDateTime.ofInstant(accessToken.getExpiresAt(), ZoneOffset.UTC))
                .build();

        memberRepository.save(member);
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }

}
