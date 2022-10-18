package com.example.weekMission.app.article.repository;

import com.example.weekMission.app.article.entity.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.weekMission.app.article.entity.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> getQslArticlesOrderByIdDesc() {
        return jpaQueryFactory
                .select(article)
                .from(article)
                .orderBy(article.id.desc())
                .fetch();
    }
}
