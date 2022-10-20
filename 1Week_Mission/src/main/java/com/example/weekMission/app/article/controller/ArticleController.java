package com.example.weekMission.app.article.controller;

import com.example.weekMission.app.article.entity.Article;
import com.example.weekMission.app.article.exception.AuthorCanNotDeleteException;
import com.example.weekMission.app.article.form.ArticleForm;
import com.example.weekMission.app.article.service.ArticleService;
import com.example.weekMission.app.member.entity.Member;
import com.example.weekMission.app.security.dto.MemberContext;
import com.example.weekMission.utill.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String showList(Model model, @RequestParam(required = false) String kwType, @RequestParam(required = false) String kw) {

        if (kw!=null) {
            List<Article> articles = articleService.search(kwType, kw);
            model.addAttribute("articles", articles);

            return "article/list";
        }


        List<Article> articles = articleService.getArticles();

        articleService.loadForPrintData(articles);

        model.addAttribute("articles", articles);
        return "article/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid ArticleForm articleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article/write";
        }

        Article article = articleService.write(memberContext.getId(), articleForm.getSubject(), articleForm.getContent(), articleForm.getHashTagContents());

        log.debug("article : " + article);

        String msg = "%d번 글이 작성되었습니다.".formatted(article.getId());
        msg = Ut.url.encode(msg);

        return "redirect:/post/%d?msg=%s".formatted(article.getId(), msg);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Article article = articleService.getForPrintArticleById(id);
        model.addAttribute("article", article);

        return "article/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, Model model, @PathVariable Long id) {
        Article article = articleService.getForPrintArticleById(id);

        if (memberContext.memberIsNot(article.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("article", article);

        return "article/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, Model model, @PathVariable Long id, @Valid ArticleForm articleForm, BindingResult bindingResult) {
        Article article = articleService.getForPrintArticleById(id);

        if (memberContext.memberIsNot(article.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        articleService.modify(article, articleForm.getSubject(), articleForm.getContent(), articleForm.getHashTagContents());
        String msg = Ut.url.encode("%d번 게시물이 수정되었습니다.".formatted(id));
        return "redirect:/post/%d?msg=%s".formatted(id, msg);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal MemberContext memberContext, @PathVariable Long id) {
        Article article = articleService.findById(id).get();

        Member author = memberContext.getMember();

        if (articleService.authorCanDelete(author, article) == false) {
            throw new AuthorCanNotDeleteException();
        }

        articleService.delete(id);
        return "redirect:/post/list" + "?msg=" + Ut.url.encode("%d번 글이 삭제되었습니다.".formatted(article.getId()));
    }
}
