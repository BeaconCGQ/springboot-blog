package com.cgq.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Tag;
import com.cgq.boot.service.BlogService;
import com.cgq.boot.service.impl.BlogServiceImpl;
import com.cgq.boot.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BlogController {

    @Autowired
    private BlogServiceImpl blogService;

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/blogInfo/{id}")
    public String blogInfo(@PathVariable Long id, Model model){

        Blog blog = blogService.convertBlog(id);

        Tag tag = tagService.getById(blog.getTagId());

        model.addAttribute("blog",blog);
        model.addAttribute("tag",tag);

        return "blog";
    }

    //footer显示
    @GetMapping("/footer/newblog")
    public String footer(Model model){

        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.orderByDesc("update_time");
        Page<Blog> blogPage = new Page<>(1,3);
        Page<Blog> blogs = blogService.page(blogPage,queryWrapper1);


        model.addAttribute("newblogs",blogs.getRecords());
        return "_fragments :: newblogList";
    }
}
