package com.cgq.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {

   private String title;
   private Boolean recommend=false;
   private Long typeId;
}
