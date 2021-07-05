package com.inshorts.reader.controller;

import com.inshorts.reader.entity.ArticleEntity;
import com.inshorts.reader.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<String> createArticle(@RequestBody ArticleEntity articleEntity)
    {
        articleService.createArticle(articleEntity);
        return new ResponseEntity<>("New Article Created", HttpStatus.CREATED);
    }

    @GetMapping("/article/{id}")
    public ArticleEntity getArticle(@PathVariable Integer id)
    {
        return articleService.getArticle(id);
    }

    @PutMapping("/article/{id}")
    public String updateArticle(@PathVariable Integer id, @RequestBody ArticleEntity articleEntity)
    {
        articleService.updateArticle(id, articleEntity);
        return "Article has been updated.";
    }

    @PatchMapping("/article/{id}")
    public String updateArticle(@PathVariable Integer id, @RequestBody Map<String, Object> request)
    {
        articleService.updateArticle(id, request);
        return "Article has been updated.";
    }

    @GetMapping("/article")
    public List<ArticleEntity> getAllArticles(@RequestParam(name = "pageNo", required = false) Integer pageNo,@RequestParam(name = "size", required = false) Integer size)
    {
        return articleService.getAllArticles(pageNo, size);
    }

}
