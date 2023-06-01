package com.project.newsblog.service;

import com.project.newsblog.dto.ArticleDto;
import com.project.newsblog.dto.NewsDto;

import java.util.List;

public interface INewsService {
    NewsDto listNews();
    NewsDto searchNews(String query);
    NewsDto advancedSearch(String category, String language);
    void saveArticle(ArticleDto articleDto);
    List<ArticleDto> getSavedArticles();
}
