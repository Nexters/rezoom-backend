package com.nexters.rezoom.member.application;

import com.nexters.global.exception.BusinessException;
import com.nexters.global.exception.EntityNotFoundException;
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

    private final MemberRepository repository;
    private final PasswordEncoder encoder;

    public MemberService(MemberRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public MemberDto.ViewRes getMemberInfo(String id) {
        Member findMember = getMember(id);
        if (findMember == null)
            throw new EntityNotFoundException(ErrorType.MEMBER_NOT_FOUND);

        return new MemberDto.ViewRes(findMember);
    }

    public void signUp(MemberDto.SignUpReq req) {
        checkExistMember(req.getId());

        Member member = new Member(req.getId(), req.getName(), encoder.encode(req.getPassword()));
        repository.save(member);
    }

    @Transactional
    public void signUp(OAuth2Member member) {
        OAuth2Member findMember = (OAuth2Member) getMember(member.getId());
        if (findMember == null)
            repository.save(member);

            // 이미 존재하는 경우, Update 처리
        else {
            findMember.setName(member.getName());
            findMember.setAccessToken(member.getAccessToken());
            findMember.setExpiresIn(member.getExpiresIn());
        }
    }

    @Transactional
    public void updateMemberInfo(String id, MemberDto.UpdateReq req) {
        Member findMember = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorType.MEMBER_NOT_FOUND));

        findMember.updateMemberInfo(req.getName(), req.getMotto());
    }

    private Member getMember(String id) {
        Optional<Member> findMember = repository.findById(id);
        return findMember.orElse(null);
    }

    private void checkExistMember(String id) {
        repository.findById(id).ifPresent(member -> {
            throw new BusinessException(ErrorType.EMAIL_DUPLICATION);
        });
    }
}
