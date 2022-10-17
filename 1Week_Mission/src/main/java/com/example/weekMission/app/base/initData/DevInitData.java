package com.example.weekMission.app.base.initData;

import com.example.weekMission.app.article.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevInitData implements InitDataBefore {
    @Bean
    CommandLineRunner initData(
            ArticleService articleService) {
        return args -> {
            before(articleService);
        };
    }
}