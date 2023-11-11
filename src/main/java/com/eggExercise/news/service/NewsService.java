package com.eggExercise.news.service;

import com.eggExercise.news.entity.News;
import com.eggExercise.news.repository.NewsRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    /**
     * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades necesarias para
     * administrar noticias (consulta, creación, modificación y dar de baja).
     */

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void createNews(String title, String body) {
        News news = new News();

        validate(title);
        news.setTitle(title);
        
        validate(body);
        news.setBody(body);

        newsRepository.save(news);
    }

    @Transactional(readOnly = true)
    public List<News> listAll() {
        List<News> news;
        news = newsRepository.findAll();
        return news;
    }

    @Transactional
    public void modifyNews(String title, String body, Long id) {
        validate(title);
        validate(body);

        Optional<News> response = newsRepository.findById(id);

        if (response.isPresent()) {
            News news = response.get();
            news.setTitle(title);
            news.setBody(body);
            newsRepository.save(news);
        }
    }

    @Transactional(readOnly = true)
    public News getOne(Long id) {
        return newsRepository.getOne(id);
    }


    @Transactional
    public void delete(Long id)  {
        boolean exists = newsRepository.existsById(id);
        if (!exists){
            throw new IllegalStateException("The news with id " + id + " does not exists");
        }
        newsRepository.deleteById(id);
    }

    private void validate(String variable) {
        if (variable == null || variable.isEmpty()) {
            throw new IllegalStateException("The " + variable + " cannot be null or empty");
        }
    }
}
