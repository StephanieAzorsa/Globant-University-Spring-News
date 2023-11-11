package com.eggExercise.news.controller;

import com.eggExercise.news.entity.News;
import com.eggExercise.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/register")
    public String register() {
        return "news_form.html";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String title,
                               @RequestParam String body,
                               ModelMap model) {
        try {
            newsService.createNews(title, body);
            model.put("success",
                    "The news was registered successfully!");
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "news_form.html";
        }
        return "news_form.html";
    }

    @GetMapping("/admin")
    public String admin(ModelMap model) {
        model.addAttribute("allNews",
                newsService.listAll());
        return "panel_admin.html";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("allNews",
                newsService.listAll());
        return "news_list.html";
    }

    @GetMapping("/read-more/{id}")
    public String readMore(@PathVariable Long id,
                           ModelMap model) {
        News news = newsService.getOne(id);
        model.addAttribute("news", news);
        return "news.html";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable Long id,
                         ModelMap model) {
        model.put("news", newsService.getOne(id));
        return "news_modify.html";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id,
                         String title,
                         String body,
                         ModelMap model) {
        try {
            newsService.modifyNews(title, body, id);
            return "redirect:../admin";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "news_modify.html";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         ModelMap model) {
        try {
            newsService.delete(id);
            return "redirect:../admin";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "panel_admin.html";
        }
    }



}
