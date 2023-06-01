package com.project.newsblog.controller;

import com.project.newsblog.dto.ArticleDto;
import com.project.newsblog.dto.NewsDto;
import com.project.newsblog.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController {

    @Autowired
    INewsService newsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public NewsDto getTopHeadlinesNews() {
        return newsService.listNews();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto searchNews(@RequestParam String q) {
        return newsService.searchNews(q);
    }

    @GetMapping("/advanced")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto advancedNews(@RequestParam String category, @RequestParam String language) {
        return  newsService.advancedSearch(category, language);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public String saveArticle(@RequestBody ArticleDto articleDto){
        newsService.saveArticle(articleDto);
        return "Successfully Saved";
    }

    @GetMapping("/saved/articles")
    @ResponseStatus(HttpStatus.OK)
    public List<ArticleDto> getSavedArticles() {
        return newsService.getSavedArticles();
    }
}
