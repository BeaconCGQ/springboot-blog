package com.cgq.boot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.pojo.User;
import com.cgq.boot.service.UserService;
import com.cgq.boot.service.impl.BlogServiceImpl;
import com.cgq.boot.service.impl.TagServiceImpl;
import com.cgq.boot.service.impl.TypeServiceImpl;
import com.cgq.boot.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogManagerController {

    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TypeServiceImpl typeService;

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/blogsManager")
    public String ToBlog(@RequestParam(value = "pn",defaultValue = "1") Integer pn,Model model){

        List<Type> types = typeService.list();
        model.addAttribute("types",types);

        Page<Blog> blogPage = new Page<>(pn,2);

        Page<Blog> page = blogService.page(blogPage,null);

        model.addAttribute("page",page);

        return "admin/blogsManager";
    }

    @PostMapping("/blogsManager/search")
    public String ToSearchBlog(@RequestParam("page") String current, BlogQuery blogQuery,Model model){


        List<Type> types = typeService.list();
        model.addAttribute("types",types);


        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(blogQuery.getTitle()),"title",blogQuery.getTitle());
        if(blogQuery.getRecommend() == true){
            queryWrapper.eq("recommend",blogQuery.getRecommend());
        }

        if(blogQuery.getTypeId() != null){
            queryWrapper.eq("type_id",blogQuery.getTypeId());
        }

        Page<Blog> blogPage = new Page<>(Integer.parseInt(current),2);

        Page<Blog> page = blogService.page(blogPage,queryWrapper);

        model.addAttribute("page",page);

        return "admin/blogsManager :: blogList";
    }

    @GetMapping("/toBlogInput")
    public String toBlogInput(Model model){

       model.addAttribute("types",typeService.list());
       model.addAttribute("tags",tagService.list());

        return "admin/blogsInput";
    }

    @GetMapping("/toBlogInput/{id}")
    public String toBlogEdit(@PathVariable Long id,Model model){

        if(id != null){
            Blog blog = blogService.getById(id);
            model.addAttribute("blogq",blog);
        }
        model.addAttribute("types",typeService.list());
        model.addAttribute("tags",tagService.list());


        return "admin/blogsInput";
    }

    @PostMapping("/blogSaveOrUpdate")
    public String blogSaveOrUpdate(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUserId(((User) session.getAttribute("user")).getId());
        blog.setUser(((User) session.getAttribute("user")).getNickname());

        blogService.SaveOrUpdateBlog(blog);
        attributes.addFlashAttribute("success","操作成功");

        return "redirect:/admin/blogsManager";
    }

    @GetMapping("/toBlogDelete/{id}")
    public String toBlogDelete(@PathVariable Long id,RedirectAttributes attributes){

        blogService.removeById(id);
        attributes.addFlashAttribute("success","删除成功");

        return "redirect:/admin/blogsManager";
    }


}
