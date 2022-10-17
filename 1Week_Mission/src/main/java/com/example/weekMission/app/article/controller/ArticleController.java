package com.example.weekMission.app.article.controller;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String showList(Model model) {
        List<Article> articles = articleService.getList();
        model.addAttribute("articles", articles);
        return "article/list";
    }

    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }

    @PostMapping("/write")
    public String write(Article article) {
        articleService.save(article);
        return "redirect:/article/list";
    }
}
