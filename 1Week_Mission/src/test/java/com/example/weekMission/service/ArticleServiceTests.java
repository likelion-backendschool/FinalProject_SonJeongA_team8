package com.example.weekMission.service;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ArticleServiceTests {

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("게시글 업로드")
    void t1() {

        Article article = articleService.write(3L, "제목", "내용");

        assertThat(article).isNotNull();
        assertThat(article.getId()).isEqualTo(3L);
        assertThat(article.getSubject()).isEqualTo("제목");
        assertThat(article.getContent()).isEqualTo("내용");
    }
}
