package com.exam.shop.controller;

import com.exam.shop.domain.entity.User;
import com.exam.shop.domain.entity.UserRole;
import com.exam.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Profile(value = "local")
@Component
@RequiredArgsConstructor
@Slf4j
public class InitUser {

    private final InitUserService initUserService;

    @PostConstruct
    public void init(){
        initUserService.createUserInfo();
    }

    @Component
    static class InitUserService {

        private final UserService userService;

        @NotNull
        private final BCryptPasswordEncoder passwordEncoder;

        public InitUserService(UserService userService, @NotNull BCryptPasswordEncoder passwordEncoder) {
            this.userService = userService;
            this.passwordEncoder = passwordEncoder;
        }

        @Transactional
        public void createUserInfo(){
            User userVO = new User();
            userVO.setUserEmail("user@naver.com");
            userVO.setUserPw(passwordEncoder.encode("test"));
            userVO.setRole(UserRole.USER);

            if(userService.createUser(userVO) == null){
                log.error("Create User Error");
            }

            User adminVO = new User();
            adminVO.setUserEmail("admin@naver.com");
            adminVO.setUserPw(passwordEncoder.encode("test"));
            adminVO.setRole(UserRole.ADMIN);
            if(userService.createUser(adminVO) == null){
                log.error("Create Admin Error");
            }
        }

    }
}
