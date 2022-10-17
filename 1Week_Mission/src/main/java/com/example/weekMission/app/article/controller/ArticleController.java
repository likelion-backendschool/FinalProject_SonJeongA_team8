package com.example.weekMission.app.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public String showList() {
        return "article/list";
    }

    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }

    @PostMapping("/write")
    public String write() {
        return "article/list";
    }
}
