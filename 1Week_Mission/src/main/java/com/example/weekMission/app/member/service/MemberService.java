package com.example.weekMission.app.member.service;

import com.example.weekMission.app.member.entity.Member;
import com.example.weekMission.app.member.repository.MemberRepository;
import com.example.weekMission.app.security.dto.MemberContext;
import com.example.weekMission.utill.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    private static final String title = "e-book 임시 비밀번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. e-book 임시 비밀번호 안내 메일입니다. "
            +"\n" + "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주세요."+"\n";

    public Member join(String username, String password, String email, String nickname) {
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("\"" + username + "\" 님의 e-book 가입 축하 이메일입니다.");
        simpleMailMessage.setText("안녕하세요. " +"\"" + username +"\"님의 " + "e-book 가입을 축하드립니다!");
        javaMailSender.send(simpleMailMessage);

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

    public String enrolledEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if(member == null) {
            return null;
        }

        return member.getUsername();
    }

    public Member enrolledUsernameAndEmail(String username, String email) {
        Member memberUsername = memberRepository.findByUsername(username).orElse(null);

        Member memberEmail = memberRepository.findByEmail(email).orElse(null);

        if(memberUsername==null || memberEmail==null || !(memberUsername.equals(memberEmail))) {
            return null;
        }

        String randomPassword = Ut.randomPassword();

        memberEmail.setPassword(passwordEncoder.encode(randomPassword));
        memberRepository.save(memberEmail);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(memberEmail.getEmail());
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message + randomPassword);
        javaMailSender.send(simpleMailMessage);

        return memberUsername;
    }
}