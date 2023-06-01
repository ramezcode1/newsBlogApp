package com.project.newsblog.service;

import com.project.newsblog.dto.ArticleDto;
import com.project.newsblog.dto.NewsDto;
import com.project.newsblog.model.Article;
import com.project.newsblog.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NewsService implements INewsService {

    @Value("${news.api.baseurl}")
    private String baseUrl;

    @Value("${news.api.key}")
    private String apiKey;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private ArticleRepository articleRepository;
    @Override
    public NewsDto listNews() {
        String topHeadlinesUrl = baseUrl + "/top-headlines?country=us&apiKey=" + apiKey;
        return restTemplate.getForObject(topHeadlinesUrl, NewsDto.class);
    }

    @Override
    public NewsDto searchNews(String query) {
        String searchUrl = baseUrl + "/everything?q=${?}&sortBy=popularity&apiKey=" + apiKey;
        return restTemplate.getForObject(searchUrl, NewsDto.class, query);
    }

    @Override
    public NewsDto advancedSearch(String category, String language) {
        String topHeadlinesUrl = "";
        String searchQuery = "";
        if (!category.isEmpty()) {
            searchQuery += "category=" + category + "&";
        }
        if (!language.isEmpty()) {
            searchQuery += "language=" + language  + "&";
        }

        if (searchQuery.isEmpty()) {
            return listNews();
        }

        topHeadlinesUrl = baseUrl + "/top-headlines?"+ searchQuery + "apiKey=" + apiKey;
        return restTemplate.getForObject(topHeadlinesUrl, NewsDto.class);
    }

    @Override
    public void saveArticle(ArticleDto articleDto) {
        Article article = modelMapper.map(articleDto, Article.class);
        articleRepository.save(article);
    }

    @Override
    public List<ArticleDto> getSavedArticles() {
        return articleRepository.findAll().stream().map(article -> modelMapper.map(article, ArticleDto.class)).toList();
    }


}
