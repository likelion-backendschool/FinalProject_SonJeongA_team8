package com.example.weekMission.app.article.repository;


import com.example.weekMission.app.article.entity.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> getQslArticlesOrderByIdDesc();

    List<Article> getTop100ArticlesByOrderByModifyDateDesc();

    List<Article> searchQsl(String kwType, String kw);
}
