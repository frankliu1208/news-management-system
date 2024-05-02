package org.example.service;

import org.example.pojo.Article;
import org.example.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    // condition pagination list search
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
