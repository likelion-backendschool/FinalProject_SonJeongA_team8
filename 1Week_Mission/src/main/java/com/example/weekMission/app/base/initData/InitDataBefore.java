package com.example.weekMission.app.base.initData;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;
import com.example.weekMission.app.member.entity.Member;
import com.example.weekMission.app.member.service.MemberService;

public interface InitDataBefore {
    default void before(MemberService memberService, ArticleService articleService) {

        Member member1 = memberService.join("user1", "1234", "user1@test.com");
        Member member2 = memberService.join("user2", "1234", "user2@test.com");

        Article article1 = articleService.write(1L, "제목1", "내용1");
        Article article2 = articleService.write(1L, "제목2", "내용2");
        Article article3 = articleService.write(2L, "제목1", "내용1");
        Article article4 = articleService.write(2L, "제목2", "내용2");
    }
}