package com.nexters.rezoom.member.application;

import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public MemberDto.ViewRes getMemberInfo(String id) {
        Member findMember = getMember(id);
        return new MemberDto.ViewRes(findMember.getId(), findMember.getName());
    }

    public void signUp(MemberDto.SignUpReq req) {
        Member findMember = repository.findById(req.getId());
        if (findMember != null)
            throw new RuntimeException("이미 존재하는 아이디입니다.");

        repository.save(new Member(req.getId(), req.getName(), encoder.encode(req.getPassword())));
    }

    public boolean signIn(MemberDto.SignInReq req) {
        Member member = getMember(req.getId());

        boolean isSuccess = encoder.matches(req.getPassword(), member.getPassword());
        if (isSuccess) return true;
        else {
            throw new RuntimeException("패스워드가 틀렸습니다.");
        }
    }

    private Member getMember(String id) {
        Member findMember = repository.findById(id);
        if (findMember == null) {
            throw new RuntimeException("존재하지 않는 계정입니다. id : " + id);
        }
        return findMember;
    }
}
