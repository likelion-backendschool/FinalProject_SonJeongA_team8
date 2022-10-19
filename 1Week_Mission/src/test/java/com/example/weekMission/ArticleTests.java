package com.example.weekMission;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;
import com.example.weekMission.app.hashTag.entity.HashTag;
import com.example.weekMission.app.hashTag.service.HashTagService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ArticleTests {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private HashTagService hashTagService;

    @Test
    @DisplayName("1번 게시물에는 해시태그가 3개 존재한다.")
    void t1() {
        Article article = articleService.getArticleById(1L);

        List<HashTag> hashTags = hashTagService.getHashTags(article);

        assertThat(hashTags.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("1번 게시물의 해시태그를 수정하면, 기존 해시태그 중 몇개는 지워질 수 있다.")
    void t2() {
        String keywordContentsStr = "#안녕 #하이";
        Article article = articleService.getArticleById(1L);
        hashTagService.applyHashTags(article, keywordContentsStr);
    }

    @Test
    @DisplayName("해시태그 자바와 관련된 모든 게시물 조회")
    void t3() {
        List<Article> articles = articleService.search("hashTag", "자바");

        assertThat(articles.size()).isEqualTo(1);
    }

}
