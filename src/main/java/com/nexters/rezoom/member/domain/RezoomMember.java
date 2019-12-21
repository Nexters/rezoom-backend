package com.nexters.rezoom.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by momentjin@gmail.com on 2019-12-19
 * Github : http://github.com/momentjin
 */
@Entity
@DiscriminatorValue(value = "rezoom")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RezoomMember extends Member {

    @Builder(builderMethodName = "RezoomMemberBuilder")
    public RezoomMember(String id, String name, String password) {
        super(id, name, password);
    }

}