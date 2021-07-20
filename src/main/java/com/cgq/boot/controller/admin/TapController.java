package com.cgq.boot.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Tag;
import com.cgq.boot.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TapController {

    @Autowired
    private TagServiceImpl tagService;

    @GetMapping("/ToTags")
    public String Totags(@RequestParam(value = "pn",defaultValue = "1") Integer pn,Model model){

//        List<tag> tags = tagService.list();

        Page<Tag> tagPage = new Page<>(pn,3);

        Page<Tag> page = tagService.page(tagPage,null);

        model.addAttribute("tags",page);

        return "admin/tags";
    }

    @GetMapping("/tagInput/{id}")
    public String tagEdit(@PathVariable Long id, Model model){

        if(id != null){
            Tag tagq = tagService.getById(id);
            model.addAttribute("tagq",tagq);
        }

        return "admin/tagInput";
    }

    @GetMapping("/tagInput")
    public String tagInput(){

        return "admin/tagInput";
    }

   @PostMapping("/tagSaveOrUpdate")
    public String savaOrUpdate(@Valid Tag tag, BindingResult result, Model model, RedirectAttributes attributes){

        if(result.hasErrors()){
            return "admin/tagInput";
        }
       try {
           tagService.SavaOrUpdateTag(tag);
           attributes.addFlashAttribute("success","操作成功");
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("msg",e.getMessage());
           return "admin/tagInput";
       }
       return "redirect:/admin/ToTags";
    }


    @GetMapping("/tagDelete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){

        tagService.removeById(id);
        attributes.addFlashAttribute("success","删除成功");

        return "redirect:/admin/ToTags";
    }


}
