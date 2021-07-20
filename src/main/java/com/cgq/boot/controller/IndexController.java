package com.cgq.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Tag;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.service.BlogService;
import com.cgq.boot.service.impl.BlogServiceImpl;
import com.cgq.boot.service.impl.TagServiceImpl;
import com.cgq.boot.service.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TypeServiceImpl typeService;
    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/")
    public String index(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){

        List<Tag> tags1 = tagService.countBLogOfTag(tagService.list());


        Page<Tag> tagPage = new Page<>(pn,5);
        Page<Tag> tags = tagService.page(tagPage);
        tags.setRecords(tagService.countBLogOfTag(tags.getRecords()));

        model.addAttribute("tags",tags);


        List<Type> typesq = typeService.list();
        blogService.countBLogOfType(typesq);
//        Collections.sort(typesq, new Comparator<Type>() {
//            @Override
//            public int compare(Type o1, Type o2) {
//                return o1.getBlogs().size() - o2.getBlogs().size();
//            }
//        });
        Page<Type> typePage = new Page<>(pn,5);
        Page<Type> types = typeService.page(typePage);
        types.setRecords(blogService.countBLogOfType(types.getRecords()));
        model.addAttribute("types",types);


        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.orderByDesc("update_time");
        Page<Blog> blogPage = new Page<>(pn,4);
        Page<Blog> blogs = blogService.page(blogPage,queryWrapper1);

        model.addAttribute("blogs",blogs);


        Page<Blog> pageNew = new Page<>(pn,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("update_time");
        queryWrapper.eq("recommend",true);
        Page blogNew = blogService.page(pageNew, queryWrapper);
        model.addAttribute("blogNew",blogNew);

        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                         @RequestParam String query,Model model){

        System.out.println(query);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(query)){
            queryWrapper.like("title",query).or().like("content",query).or().like("description",query);
        }
        Page<Blog> blogPage = new Page<>(pn,1);
        Page<Blog> blogs = blogService.page(blogPage, queryWrapper);
        model.addAttribute("blogs",blogs);
        model.addAttribute("query",query);

        return "search";

    }

    @GetMapping("/searchGet")
    public String searchGet(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
                         @RequestParam String query,Model model){

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(query)){
            queryWrapper.like("title",query).or().like("content",query).or().like("description",query);
        }
        Page<Blog> blogPage = new Page<>(pn,1);
        Page<Blog> blogs = blogService.page(blogPage, queryWrapper);
        model.addAttribute("blogs",blogs);
        model.addAttribute("query",query);

        return "search";

    }
}
