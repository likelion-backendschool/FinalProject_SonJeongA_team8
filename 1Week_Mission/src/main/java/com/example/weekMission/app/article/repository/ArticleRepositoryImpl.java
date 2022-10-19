package com.example.weekMission.app.article.repository;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.utill.Ut;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.weekMission.app.article.entity.QArticle.article;
import static com.example.weekMission.app.hashTag.entity.QHashTag.hashTag;
import static com.example.weekMission.app.keyword.entity.QKeyword.keyword;


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

    @Override
    public List<Article> getTop100ArticlesByOrderByModifyDateDesc() {
        return jpaQueryFactory
                .select(article)
                .from(article)
                .orderBy(article.modifyDate.desc())
                .limit(100)
                .fetch();
    }

    @Override
    public List<Article> searchQsl(String kwType, String kw) {

        JPAQuery<Article> jpaQuery = jpaQueryFactory
                .select(article)
                .distinct()
                .from(article);

        if (Ut.str.empty(kw) == false) {
            if (Ut.str.eq(kwType, "hashTag")) {
                jpaQuery
                        .innerJoin(hashTag)
                        .on(article.eq(hashTag.article))
                        .innerJoin(keyword)
                        .on(keyword.eq(hashTag.keyword))
                        .where(keyword.content.eq(kw));
            }
        }

        jpaQuery.orderBy(article.id.desc());

        return jpaQuery.fetch();
    }
}
