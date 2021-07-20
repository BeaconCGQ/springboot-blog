package com.cgq.boot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_type")
public class Type {

//    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @NotEmpty(message = "分类名称不能为空")
    private String name;

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();

//    private List<Blog> blogs = new ArrayList<>();

}
