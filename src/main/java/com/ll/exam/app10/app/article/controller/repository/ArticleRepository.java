package com.ll.exam.app10.app.article.controller.repository;

import com.ll.exam.app10.app.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
