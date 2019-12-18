package com.nexters.config.security;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository repository;

    public UserDetailsServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> findMember = repository.findById(username);
        Member member = findMember.orElseThrow(() -> new UsernameNotFoundException(username));

        return new CustomUserDetail(member.getId(), member.getPassword(), member.getName());
    }

}