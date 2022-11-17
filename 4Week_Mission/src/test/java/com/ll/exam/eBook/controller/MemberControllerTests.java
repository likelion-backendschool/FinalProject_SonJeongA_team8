package com.ll.exam.eBook.controller;

import com.ll.exam.eBook.app.member.controller.MemberController;
import com.ll.exam.eBook.app.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MemberControllerTests {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 폼")
    void t1() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/member/join"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showJoin"))
                .andExpect(content().string(containsString("회원가입")));
    }

    @Test
    @DisplayName("회원가입")
    void t2() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/member/join")
                        .with(csrf())
                        .param("username", "user10")
                        .param("password", "1234")
                        .param("email", "user1@test.com")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(redirectedUrlPattern("/member/login?msg=**"));

        assertThat(memberService.findByUsername("user10").isPresent()).isTrue();
    }

    @Test
    @DisplayName("아이디찾기 폼")
    void t3() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/member/findUsername"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showFindUsername"))
                .andExpect(content().string(containsString("아이디 찾기")));
    }

    @Test
    @DisplayName("아이디찾기")
    void t4() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/member/findUsername")
                        .with(csrf())
                        .param("email", "user1@test.com")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("findUsername"))
                .andExpect(redirectedUrlPattern("/member/login?**"));
    }

    @Test
    @DisplayName("비밀번호 찾기 폼")
    void t5() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/member/findPassword"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showFindPassword"))
                .andExpect(content().string(containsString("비밀번호 찾기")));
    }

    @Test
    @DisplayName("비밀번호 찾기")
    void t6() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(post("/member/findPassword")
                        .with(csrf())
                        .param("email", "user1@test.com")
                        .param("username", "user1")
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("findPassword"))
                .andExpect(redirectedUrlPattern("/member/login?**"));
    }

    @Test
    @DisplayName("프로파일 폼")
    @WithUserDetails("user2")
    void t7() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("profile"))
                .andExpect(model().attributeExists("actorRestCash"))
                .andExpect(content().string(containsString("마이페이지")));
    }

    @Test
    @DisplayName("POST /api/v1/member/login 은 로그인 처리 URL 이다.")
    void t8() throws Exception {
        // When
        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/member/login")
                                .content("""
                                        {
                                            "username": "user1",
                                            "password": "1234"
                                        }
                                        """.stripIndent())
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());
    }
}
