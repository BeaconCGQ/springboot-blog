package com.cgq.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgq.boot.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
