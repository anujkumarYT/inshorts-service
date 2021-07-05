package com.inshorts.reader.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer articleId;

    private String title;
    private String description;
    private String addedBy;
    private Timestamp timestamp;

    @ManyToMany
    @JoinTable(
            name = "article_category",
            joinColumns = @JoinColumn(name = "articleId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    private List<CategoryEntity> categories;


}
