package com.example.weekMission.app.article.service;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.repository.ArticleRepository;
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

    public void save(Article article) {
        articleRepository.save(article);
    }
}
