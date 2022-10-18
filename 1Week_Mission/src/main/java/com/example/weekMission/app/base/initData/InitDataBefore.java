package com.example.weekMission.app.base.initData;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;
import com.example.weekMission.app.member.entity.Member;
import com.example.weekMission.app.member.service.MemberService;

public interface InitDataBefore {
    default void before(MemberService memberService, ArticleService articleService) {

        Member member1 = memberService.join("user1", "1234", "user1@test.com", "p1");
        Member member2 = memberService.join("user2", "1234", "user2@test.com", "p2");

        Article article1 = articleService.write(1L, "제목1", "내용1", "#자바 #개발 #프로그래밍");
        Article article2 = articleService.write(1L, "제목2", "내용2", "#파이썬 #개발 #프로그래밍");
        Article article3 = articleService.write(2L, "제목1", "내용1", "#C언어 #개발 #프로그래밍");
        Article article4 = articleService.write(2L, "제목2", "내용2", "#Swift #개발 #프로그래밍");
    }
}