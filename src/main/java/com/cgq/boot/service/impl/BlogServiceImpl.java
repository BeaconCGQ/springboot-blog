package com.cgq.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgq.boot.mapper.BlogMapper;
import com.cgq.boot.mapper.TagMapper;
import com.cgq.boot.mapper.TypeMapper;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.service.BlogService;
import com.cgq.boot.utils.MarkdownUtils;
import org.commonmark.node.Link;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
   private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private TagMapper tagMapper;

    public int SaveOrUpdateBlog(Blog blog){

        blog.setType(typeMapper.selectById(blog.getTypeId()).getName());

        if(blog.getId() == null){

            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
           return blogMapper.insert(blog);
        }else {
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",blog.getId());

            blog.setUpdateTime(new Date());
          return blogMapper.update(blog,queryWrapper);
        }
    }

    public List<Type> countBLogOfType(List<Type> types){

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        for (Type type : types) {

            queryWrapper.eq("type_id",type.getId());
            List<Blog> blogs = blogMapper.selectList(queryWrapper);
            type.setBlogs(blogs);
            queryWrapper.clear();
        }
        return types;
    }

    public Blog convertBlog(Long id){


        Blog blog = blogMapper.selectById(id);
        blog.setViews(blog.getViews() + 1);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        blogMapper.update(blog,queryWrapper);

        //设定数据库仍然保存文本
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);

        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        return b;
    }

    public Map<String,List<Blog>> archiveBlog(){

        List<String> years = blogMapper.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        for (String year : years){
            queryWrapper.like("update_time",year);
            List<Blog> blogs = blogMapper.selectList(queryWrapper);
            map.put(year,blogs);
            queryWrapper.clear();
        }
        return map;
    }


}
