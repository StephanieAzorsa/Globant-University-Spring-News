package com.eggExercise.news.repository;

import com.eggExercise.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>{
    @Query("SELECT n FROM News n WHERE n.title = :title")
    public News findNewsByTitle(@Param("title") String title);
}
