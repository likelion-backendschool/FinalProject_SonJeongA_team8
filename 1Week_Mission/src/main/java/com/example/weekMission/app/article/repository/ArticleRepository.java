package com.example.weekMission.app.article.repository;

import com.example.weekMission.app.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
