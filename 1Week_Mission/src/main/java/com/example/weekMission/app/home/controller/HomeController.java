package com.example.weekMission.app.home.controller;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<Article> articles = articleService.getRecentArticles();

        articleService.loadForPrintData(articles);

        model.addAttribute("articles", articles);
        return "home/main";
    }
}
