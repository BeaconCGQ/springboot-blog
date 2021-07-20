package com.cgq.boot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_tag")
public class Tag {

    private Long id;

    @NotEmpty(message = "标签不能为空")
    private String name;

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();

//    @TableField(exist = false)
//    private Integer count;

}
