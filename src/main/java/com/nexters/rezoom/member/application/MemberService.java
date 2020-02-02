package com.nexters.rezoom.member.application;

import com.nexters.global.exception.BusinessException;
import com.nexters.global.exception.ErrorType;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import com.nexters.rezoom.member.domain.OAuth2Member;
import com.nexters.rezoom.member.dto.MemberDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberDto.ViewRes getMemberInfo(String id) {
        Member findMember = getMember(id);
        if (findMember == null)
            throw new BusinessException(ErrorType.MEMBER_NOT_FOUND);

        return new MemberDto.ViewRes(findMember);
    }

    public void signUp(MemberDto.SignUpReq req) {
        memberRepository.findById(req.getId()).ifPresent(member -> {
            throw new BusinessException(ErrorType.EMAIL_DUPLICATION);
        });

        Member member = new Member(req.getId(), req.getName(), passwordEncoder.encode(req.getPassword()));
        memberRepository.save(member);
    }

    @Transactional
    public void signUp(OAuth2Member member) {
        OAuth2Member findMember = (OAuth2Member) getMember(member.getId());
        if (findMember == null)
            memberRepository.save(member);

            // 이미 존재하는 경우, Update 처리
        else {
            findMember.updateOAuth2MemberInfo(
                    member.getName(),
                    member.getAccessToken(),
                    member.getExpiresIn());
        }
    }

    @Transactional
    public void updateMemberInfo(String id, MemberDto.UpdateReq req) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorType.MEMBER_NOT_FOUND));

        findMember.updateMemberInfo(req.getName(), req.getMotto());
    }

    private Member getMember(String id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return findMember.orElse(null);
    }
}
