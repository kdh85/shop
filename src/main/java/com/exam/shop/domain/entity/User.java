package com.exam.shop.domain.entity;

import com.exam.shop.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "userEmail", "userPw"})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    private String userEmail;

    @Setter
    private String userPw;

    @Setter
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Setter
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isEnable = true;

    @Builder
    public User(String userEmail, String userPw) {
        this.userEmail = this.userEmail;
        this.userPw = userPw;
    }
}
