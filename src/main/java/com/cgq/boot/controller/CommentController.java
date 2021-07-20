package com.cgq.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgq.boot.mapper.CommentMapper;
import com.cgq.boot.pojo.Blog;
import com.cgq.boot.pojo.Comment;
import com.cgq.boot.pojo.User;
import com.cgq.boot.service.CommentService;
import com.cgq.boot.service.impl.BlogServiceImpl;
import com.cgq.boot.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private BlogServiceImpl blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        List<Comment> comments = commentService.listCommentByBlogId(blogId);
//        List<Comment> commentsNot = commentService.listCommentByBlogIdNot(blogId);

//
//        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
//        for (Comment comment : comments) {
//            queryWrapper.eq("parent_comment_id",comment.getId());
//            List<Comment> replyComment = commentService.list(queryWrapper);
//
//            for (Comment comment1 : replyComment) {
//                comment1.setParentComment(commentService.getById(comment.getId()));
//            }
//            queryWrapper.clear();
//            comment.setReplyComments(replyComment);
//        }





//        for (Comment comment : commentsNot) {
//
//            Comment commentParent = commentService.getById(comment.getParentCommentId());
//            comment.setParentComment(commentParent);
//        }

        model.addAttribute("comments",comments);

//        model.addAttribute("commentNot",commentsNot);


        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, Model model, HttpSession session){




        User user = (User) session.getAttribute("user");

        if(user != null){
           comment.setAvatar(user.getAvatar());
           comment.setAdminComment(true);
        }else {

            comment.setAvatar(avatar);
        }

        if (comment.getParentComment().getId() != null) {
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.savaComment(comment);

        List<Comment> comments = commentService.listCommentByBlogId(comment.getBlogId());

        model.addAttribute("comments",comments);

//        return "redirect:/comments/" + comment.getBlogId();

        return "blog :: commentList";
    }
}
