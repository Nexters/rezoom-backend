package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.domain.member.domain.MemberRepository;
import com.nexters.rezoom.core.domain.member.domain.OAuth2Member;
import org.springframework.stereotype.Service;

/**
 * Created by momentjin@gmail.com on 2020-02-03
 * Github : http://github.com/momentjin
 */

@Service
public class OAuth2MemberService {

    private final MemberRepository memberRepository;

    public OAuth2MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void signUp(OAuth2Member member) {
        OAuth2Member findMember = (OAuth2Member) memberRepository.findById(member.getId())
                .orElse(null);

        if (findMember == null) {
            memberRepository.save(member);
        }

        // 이미 존재하는 경우, Update 처리
        else {
            findMember.updateOAuth2MemberInfo(
                    member.getName(),
                    member.getAccessToken(),
                    member.getExpiresIn());
        }
    }
}
