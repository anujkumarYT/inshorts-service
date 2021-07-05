package com.inshorts.reader.repository;

import com.inshorts.reader.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
}
