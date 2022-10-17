package com.example.weekMission.app.security.dto;

import com.example.weekMission.app.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class MemberContext extends User {

    private final Long id;

    private final LocalDateTime createDate;

    private final LocalDateTime modifyDate;

    private final String username;

    private final String email;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);

        this.id = member.getId();

        this.createDate = member.getCreateDate();

        this.modifyDate = member.getModifyDate();

        this.username = member.getUsername();

        this.email = member.getEmail();
    }

    public Member getMember() {
        return Member
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .email(email)
                .build();
    }

    public String getName() {
        return getUsername();
    }
}