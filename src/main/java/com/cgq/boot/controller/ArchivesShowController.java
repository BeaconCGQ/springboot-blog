package com.cgq.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesShowController {

    @Autowired
    private BlogServiceImpl blogService;

    @GetMapping("/archives")
    public String archivesShow(Model model){

      model.addAttribute("archiveMap",blogService.archiveBlog());

     model.addAttribute("blogsCount",blogService.list().size());
        return "archives";
    }
}
