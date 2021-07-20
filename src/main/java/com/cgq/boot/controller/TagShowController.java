package com.cgq.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Tag;
import com.cgq.boot.service.impl.BlogServiceImpl;
import com.cgq.boot.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private BlogServiceImpl blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id, @RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){

        List<Tag> tags = tagService.list();

        tagService.countBLogOfTag(tags);

        if(id == -1){
             id = tags.get(0).getId();
        }

        model.addAttribute("tags",tags);

        QueryWrapper<Blog> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("tag_id",id);

        Page<Blog> blogPage = new Page<>(pn,5);

        Page<Blog> blogs = blogService.page(blogPage, queryWrapper);

        for (Blog record : blogs.getRecords()) {
            record.setTag(tagService.getById(record.getTagId()).getName());
        }

        model.addAttribute("blogs",blogs);
        model.addAttribute("activeTagId",id);

        return "tags";
    }
}
