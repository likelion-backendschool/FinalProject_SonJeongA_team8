package com.example.weekMission.app.member.controller;

import com.example.weekMission.app.member.entity.Member;
import com.example.weekMission.app.member.form.JoinForm;
import com.example.weekMission.app.member.service.MemberService;
import com.example.weekMission.app.security.dto.MemberContext;
import com.example.weekMission.utill.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/member/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        return "member/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        memberService.join(joinForm.getUsername(), joinForm.getPassword(), joinForm.getEmail(),joinForm.getNickname());

        return "redirect:/member/login?msg=" + Ut.url.encode("회원가입이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String showModify() {
        return "member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, String email, String nickname) {

        Member member = memberService.getMemberById(memberContext.getId());

        memberService.modify(memberContext, member, email, nickname);

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberContext, member.getPassword(), memberContext.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/modify?msg=" + Ut.url.encode("회원정보 수정이 완료되었습니다.");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyPassword")
    public String showModifyPassword() {
        return "member/modifyPassword";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal MemberContext memberContext, String oldPassword, String password) {

        Member member = memberService.getMemberById(memberContext.getId());

        if(!memberService.checkPassword(member, oldPassword)) {
            return "redirect:/member/modifyPassword?errorMsg=" + Ut.url.encode("현재 비밀번호가 일치하지 않습니다.");
        }

        memberService.modifyPassword(member, password);

        return "redirect:/member/modifyPassword?msg=" + Ut.url.encode("비밀번호 변경이 완료되었습니다.");
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findUsername")
    public String showFindUsername() {
        return "member/findUsername";
    }

}