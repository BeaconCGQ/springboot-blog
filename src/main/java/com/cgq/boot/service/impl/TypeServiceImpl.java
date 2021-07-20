package com.cgq.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgq.boot.mapper.TypeMapper;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
   private TypeMapper typeMapper;

    public boolean checkRepeat(Type type){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name",type.getName());
        List list = typeMapper.selectList(queryWrapper);
        System.out.println("集合为" + list);

        if(!typeMapper.selectList(queryWrapper).isEmpty()){

            return false;
        }else {
            return true;
        }

    }


    public int SavaOrUpdateType(Type type) throws Exception {

        if(type.getId() == null){
            if(checkRepeat(type)){
                return typeMapper.insert(type);
            }else {
                throw new Exception("名称已存在，不能重复添加");
            }
        }else {
            QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",type.getId());
           return typeMapper.update(type,queryWrapper);
        }
    }

}
