package com.example.weekMission.app.base.initData;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;

public interface InitDataBefore {
    default void before(ArticleService articleService) {

        Article article1 = articleService.write("제목1", "제목1");
        Article article2 = articleService.write("제목2", "제목2");
    }
}