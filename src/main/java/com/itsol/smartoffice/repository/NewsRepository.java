package com.itsol.smartoffice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itsol.smartoffice.model.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer>, PagingAndSortingRepository<News, Integer> {

@Query("SELECT e FROM News e")
List<News> findNews(Pageable pageable);

News findByNewsId(Integer newsId);

Page<News> findAll(Pageable pageable);
}