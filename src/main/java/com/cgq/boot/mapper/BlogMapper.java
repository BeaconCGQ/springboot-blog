package com.cgq.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgq.boot.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    @Select("SELECT DATE_FORMAT(update_time,'%Y') as year FROM t_blog b GROUP BY DATE_FORMAT(update_time,'%Y') order by year desc;")
    List<String> findGroupYear();
}
