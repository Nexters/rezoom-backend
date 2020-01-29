package com.nexters.global.config.security.oauth2;

import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.OAuth2Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by momentjin@gmail.com on 2019-12-11
 * Github : http://github.com/momentjin
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class MyOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private MemberService memberService;

    @Autowired
    public MyOAuth2AuthorizedClientService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        String providerType = oAuth2AuthorizedClient.getClientRegistration().getRegistrationId();
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = (String) oauth2User.getAttributes().get("id");
        String name = oauth2User.getName();

        OAuth2Member member = OAuth2Member.OAuth2MemberBuilder()
                .id(id)
                .name(name)
                .providerType(providerType)
                .accessToken(accessToken.getTokenValue())
                .expiresIn(LocalDateTime.ofInstant(accessToken.getExpiresAt(), ZoneOffset.UTC))
                .build();

        memberService.signUp(member);
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {
        throw new NotImplementedException();
    }

}
