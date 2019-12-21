package com.nexters.rezoom.member.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2019-12-19
 * Github : http://github.com/momentjin
 */

@Entity
@DiscriminatorValue(value = "oauth2")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Member extends Member {

    @Column(name = "provider_type")
    private String providerType;

    @Setter
    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Setter
    @Column(name = "expires_it")
    private LocalDateTime expiresIn;

    @Builder(builderMethodName = "OAuth2MemberBuilder")
    public OAuth2Member(String id, String name, String providerType, String accessToken, String refreshToken, LocalDateTime expiresIn) {
        super.id = id;
        super.name = name;
        this.providerType = providerType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

}
