package com.example.weekMission.app.base.initData;

import com.example.weekMission.app.article.service.ArticleService;
import com.example.weekMission.app.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("test")
public class TestInitData implements InitDataBefore {
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            PasswordEncoder passwordEncoder,
            ArticleService articleService) {
        return args -> {
            before(memberService, articleService);
        };
    }
}