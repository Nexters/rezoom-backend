package com.nexters.rezoom.member.application;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.config.exception.ErrorCode;
import com.nexters.rezoom.config.exception.InvalidValueException;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
        return new MemberDto.ViewRes(findMember);
    }

    public void signUp(MemberDto.SignUpReq req) {
        Member findMember = repository.findById(req.getId());
        if (findMember != null)
            throw new InvalidValueException(ErrorCode.EMAIL_DUPLICATION);

        repository.save(new Member(req.getId(), req.getName(), encoder.encode(req.getPassword())));
    }

    public void updateMemberInfo(String id, MemberDto.UpdateReq req) {
        // TODO : object mapping 필요
        Member findMember = getMember(id);

        if (!req.getName().isEmpty())
            findMember.setName(req.getName());

        if (!req.getProfileImageUrl().isEmpty())
            findMember.setProfileImageUrl(req.getProfileImageUrl());

        if (!req.getMotto().isEmpty())
            findMember.setMotto(req.getMotto());
    }

    private Member getMember(String id) {
        Member findMember = repository.findById(id);
        if (findMember == null)
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND);

        return findMember;
    }
}
