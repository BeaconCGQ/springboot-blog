package com.cgq.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.service.impl.BlogServiceImpl;
import com.cgq.boot.service.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeServiceImpl typeService;

    @Autowired
    private BlogServiceImpl blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id,@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){


        List<Type> types = typeService.list();

        blogService.countBLogOfType(types);

        if(id == -1){
           id = types.get(0).getId();
        }
        model.addAttribute("types",types);

        QueryWrapper<Blog> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("type_id",id);

        Page<Blog> blogPage = new Page<>(pn,5);
        Page<Blog> blogs = blogService.page(blogPage, queryWrapper);

        model.addAttribute("blogs",blogs);
        model.addAttribute("activeTypeId",id);

        return "types";
    }
}
