package com.example.weekMission.controller;

import com.example.weekMission.app.article.controller.ArticleController;
import com.example.weekMission.app.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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
public class ArticleControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("글 작성폼")
    @WithUserDetails("user1")
    void t1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/post/write")
                )
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showWrite"))
                .andExpect(content().string(containsString("글 작성")));
    }

    @Test
    @DisplayName("글 작성")
    @WithUserDetails("user1")
    void t2() throws Exception {
        ResultActions resultActions = mvc
                .perform(post("/post/write")
                        .param("subject", "제목")
                        .param("content", "내용")
                        .param("hashTagContents", "#자바 #개발 #인프라")
                        .with(csrf())
                )
                .andDo(print());

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("write"))
                .andExpect(redirectedUrlPattern("/post/**"));

        Long articleId = Long.valueOf(resultActions.andReturn().getResponse().getRedirectedUrl().replace("/post/", "").split("\\?", 2)[0]);

        assertThat(articleService.findById(articleId).isPresent()).isTrue();
    }

    @Test
    @DisplayName("글 수정폼")
    @WithUserDetails("user1")
    void t3() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/post/1/modify"))
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(ArticleController.class))
                .andExpect(handler().methodName("showModify"))
                .andExpect(content().string(containsString("글 수정")));
    }

}
