package com.exam.shop.domain.entity;

import com.exam.shop.domain.BaseWriterEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"})
public class Member extends BaseWriterEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @Embedded
    private Address address;

    //사용자 생성 메소드.
    public static Member makeMember(String username, int age) {
        Member newMember = new Member();
        newMember.setUsername(username);
        newMember.setAge(age);
        return newMember;
    }

    //사용자 생성 메소드(주소포함).
    public static Member makeMemberWithAddress(String username, int age, Address address) {
        Member newMember = new Member();
        newMember.setUsername(username);
        newMember.setAge(age);
        newMember.setAddress(address);
        return newMember;
    }
}
