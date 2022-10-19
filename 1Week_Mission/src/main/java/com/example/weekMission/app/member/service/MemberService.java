package com.example.weekMission.app.member.service;

import com.example.weekMission.app.member.entity.Member;
import com.example.weekMission.app.member.repository.MemberRepository;
import com.example.weekMission.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(String username, String password, String email, String nickname) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Member getMemberById(Long loginedMemberId) {
        Member member = memberRepository.findById(loginedMemberId).orElse(null);
        return member;
    }

    public void modify(MemberContext memberContext, Member member, String email, String nickname) {
        member.setEmail(email);
        member.setNickname(nickname);
        memberRepository.save(member);

        memberContext.setModifyDate(member.getModifyDate());
        memberContext.setEmail(member.getEmail());
        memberContext.setNickname(member.getNickname());
    }

    public boolean checkPassword(Member member, String oldPassword) {
        if(passwordEncoder.matches(oldPassword, member.getPassword())) {
            return true;
        }

        return false;
    }

    public void modifyPassword(Member member, String password) {
        member.setPassword((passwordEncoder.encode(password)));
        memberRepository.save(member);
    }
}