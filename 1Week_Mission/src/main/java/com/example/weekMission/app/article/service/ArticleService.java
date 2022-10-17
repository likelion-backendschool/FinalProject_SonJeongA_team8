package com.example.weekMission.app.article.service;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.repository.ArticleRepository;
import com.example.weekMission.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return articleRepository.findAll();
    }

    public Article write(Long authorId, String title, String content) {
        Article article = Article
                .builder()
                .author(new Member(authorId))
                .title(title)
                .content(content)
                .build();

        articleRepository.save(article);

        return article;
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }
}
