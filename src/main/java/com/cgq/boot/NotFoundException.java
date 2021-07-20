package com.cgq.boot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
  * @description:
  * @author: 86173
  * @date: 2021/7/16 21:28
  */
 @ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

     public NotFoundException() {
     }

     public NotFoundException(String message) {
         super(message);
     }

     public NotFoundException(String message, Throwable cause) {
         super(message, cause);
     }
 }
