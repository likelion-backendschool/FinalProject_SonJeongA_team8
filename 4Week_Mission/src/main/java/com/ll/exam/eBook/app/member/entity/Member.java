package com.ll.exam.eBook.app.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.exam.eBook.app.base.entity.BaseEntity;
import com.ll.exam.eBook.app.member.entity.emum.AuthLevel;
import com.ll.exam.eBook.util.Util;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private boolean emailVerified;
    private long restCash;
    private String nickname;

    @Convert(converter = AuthLevel.Converter.class)
    private AuthLevel authLevel;

    public static Member fromJwtClaims(Map<String, Object> jwtClaims) {
        long id = (long)(int)jwtClaims.get("id");
        LocalDateTime createDate = Util.date.bitsToLocalDateTime((List<Integer>)jwtClaims.get("createDate"));
        LocalDateTime modifyDate = Util.date.bitsToLocalDateTime((List<Integer>)jwtClaims.get("modifyDate"));
        String username = (String)jwtClaims.get("username");
        String email = (String)jwtClaims.get("email");

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
        if (nickname != null) {
            return nickname;
        }

        return username;
    }

    public Member(long id) {
        super(id);
    }

    public String getJdenticon() {
        return "member__" + getId();
    }


    public List<GrantedAuthority> genAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        if (getAuthLevel() == AuthLevel.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        // 닉네임을 가지고 있다면 작가의 권한을 가진다.
        if (StringUtils.hasText(nickname)) {
            authorities.add(new SimpleGrantedAuthority("AUTHOR"));
        }

        return authorities;
    }


    // 현재 회원이 가지고 있는 권한들을 List<GrantedAuthority> 형태로 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        return authorities;
    }

    public Map<String, Object> getAccessTokenClaims() {
        return Util.mapOf(
                "id", getId(),
                "createDate", getCreateDate(),
                "modifyDate", getModifyDate(),
                "username", getUsername(),
                "email", getEmail(),
                "authLevel", getAuthLevel(),
                "authorities", getAuthorities()
        );
    }
}
