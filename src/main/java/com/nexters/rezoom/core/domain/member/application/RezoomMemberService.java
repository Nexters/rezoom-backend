package com.nexters.rezoom.core.domain.member.application;

import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.member.domain.MemberRepository;
import com.nexters.rezoom.core.domain.member.domain.OAuth2Member;
import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RezoomMemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public RezoomMemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
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
