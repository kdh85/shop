package com.exam.shop.service;

import com.exam.shop.domain.vo.UserDetailsVO;
import com.exam.shop.exception.UserNotFoundException;
import com.exam.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsVO loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UserDetailsVO userDetailsVO = userRepository
                .findByUserEmail(userEmail)
                .map(u -> new UserDetailsVO(u, Collections.singleton(new SimpleGrantedAuthority(u.getRole().getValue()))))
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        System.out.println("userDetailsVO = " + userDetailsVO.toString());
        return userDetailsVO;
    }
}
