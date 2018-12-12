package com.nexters.rezoom.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        ApplicationUser applicationUser = applicationUserRepository.selectOneByUsernameOnSecurity(username);
//
//        if (applicationUser == null) {
//            throw new UsernameNotFoundException(username);
//        }

        //return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
        return null;
    }
}