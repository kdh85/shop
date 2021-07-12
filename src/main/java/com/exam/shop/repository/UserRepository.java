package com.exam.shop.repository;

import com.exam.shop.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserEmail(String userMail);

    User findByUserEmailAndUserPw(String userId, String userPw);
}
