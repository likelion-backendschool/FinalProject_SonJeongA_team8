package com.example.weekMission.app.article.service;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.repository.ArticleRepository;
import com.example.weekMission.app.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    public boolean authorCanModify(Member author, Article article) {
        return author.getId().equals(article.getAuthor().getId());
    }

    @Transactional
    public void modify(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
    }
}
