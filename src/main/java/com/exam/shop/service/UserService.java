package com.exam.shop.service;

import com.exam.shop.domain.entity.User;
import com.exam.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService{

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User userVO) {
        return userRepository.save(userVO);
    }

    public Optional<User> findUserByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

}
