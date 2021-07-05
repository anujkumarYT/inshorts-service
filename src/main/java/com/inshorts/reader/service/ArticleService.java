package com.inshorts.reader.service;

import com.inshorts.reader.entity.ArticleEntity;
import com.inshorts.reader.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<ArticleEntity> getAllArticles()
    {
        return articleRepository.findAll();
    }

    public void createArticle(ArticleEntity articleEntity)
    {
        articleEntity.setTimestamp(new Timestamp(new Date().getTime()));
        articleRepository.save(articleEntity);
    }

    public ArticleEntity getArticle(Integer id)
    {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found.");
        });
    }

    public void updateArticle(Integer id, ArticleEntity articleEntity)
    {
        articleRepository.findById(id).ifPresent(article -> articleEntity.setArticleId(article.getArticleId()));
        articleEntity.setTimestamp(new Timestamp(new Date().getTime()));
        articleRepository.save(articleEntity);
    }

    public void updateArticle(Integer id, Map<String, Object> request)
    {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found.");
        });

        request.forEach((k,v) -> {

            Field field = ReflectionUtils.findField(ArticleEntity.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, articleEntity, v);

        });
        articleRepository.save(articleEntity);
    }

    public List<ArticleEntity> getAllArticles(Integer pageNo, Integer size) {

        if (Objects.isNull(pageNo) && Objects.isNull(size))
        {
            return articleRepository.findAll();
        }

        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("timestamp").descending());
        Page<ArticleEntity> page = articleRepository.findAll(pageable);
        return page.stream().collect(Collectors.toList());
    }
}
