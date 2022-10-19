package com.example.weekMission.app.security.dto;

import com.example.weekMission.app.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class MemberContext extends User {

    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String username;

    private String email;

    private String nickname;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);

        this.id = member.getId();

        this.createDate = member.getCreateDate();

        this.modifyDate = member.getModifyDate();

        this.username = member.getUsername();

        this.email = member.getEmail();

        this.nickname = member.getNickname();
    }

    public Member getMember() {
        return Member
                .builder()
                .id(id)
                .createDate(createDate)
                .modifyDate(modifyDate)
                .username(username)
                .email(email)
                .nickname(nickname)
                .build();
    }

    public String getName() {
        return getUsername();
    }

    private boolean memberIs(Member member) {
        return id.equals(member.getId());
    }

    public boolean memberIsNot(Member member) {
        return memberIs(member) == false;
    }

}