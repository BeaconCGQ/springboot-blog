package com.cgq.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgq.boot.mapper.BlogMapper;
import com.cgq.boot.mapper.TagMapper;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Tag;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    public boolean checkRepeat(Tag tag){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",tag.getName());
        List list = tagMapper.selectList(queryWrapper);
        System.out.println("集合为" + list);

        if(!tagMapper.selectList(queryWrapper).isEmpty()){

            return false;
        }else {
            return true;
        }

    }


    public int SavaOrUpdateTag(Tag tag) throws Exception {

        if(tag.getId() == null){
            if(checkRepeat(tag)){
                return tagMapper.insert(tag);
            }else {
                throw new Exception("标签已存在，不能重复添加");
            }
        }else {
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",tag.getId());
            return tagMapper.update(tag,queryWrapper);
        }
    }

    public List<Tag> countBLogOfTag(List<Tag> tagList){

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

//        List<Tag> tags = new ArrayList<>();


        for (Tag tag : tagList) {

            queryWrapper.eq("tag_id",tag.getId());
            List<Blog> blogs = blogMapper.selectList(queryWrapper);
            tag.setBlogs(blogs);
            queryWrapper.clear();
        }
        return tagList;
    }
}
