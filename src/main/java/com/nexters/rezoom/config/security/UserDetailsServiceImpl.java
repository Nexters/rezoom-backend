package com.nexters.rezoom.config.security;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = repository.findById(username);
        if (findMember == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetail(findMember.getId(), findMember.getPassword(), findMember.getName());
    }

}