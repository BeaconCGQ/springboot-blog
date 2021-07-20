package com.cgq.boot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cgq.boot.pojo.Type;
import com.cgq.boot.service.TypeService;
import com.cgq.boot.service.UserService;
import com.cgq.boot.service.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeServiceImpl typeService;

    @GetMapping("/ToTypes")
    public String ToTypes(@RequestParam(value = "pn",defaultValue = "1") Integer pn,Model model){

//        List<Type> types = typeService.list();

        Page<Type> typePage = new Page<>(pn,3);

        Page<Type> page = typeService.page(typePage,null);

        model.addAttribute("types",page);

        return "admin/types";
    }

    @GetMapping("/typeInput/{id}")
    public String TypeEdit(@PathVariable Long id, Model model){

        if(id != null){
            Type typeq = typeService.getById(id);
            model.addAttribute("typeq",typeq);
        }

        return "admin/typeInput";
    }

    @GetMapping("/typeInput")
    public String TypeInput(){

        return "admin/typeInput";
    }

   @PostMapping("/typeSaveOrUpdate")
    public String savaOrUpdate(@Valid Type type, BindingResult result, Model model, RedirectAttributes attributes){

        if(result.hasErrors()){
            return "admin/typeInput";
        }
       try {
           typeService.SavaOrUpdateType(type);
           attributes.addFlashAttribute("success","操作成功");
       } catch (Exception e) {
           e.printStackTrace();
           model.addAttribute("msg",e.getMessage());
           return "admin/typeInput";
       }
       return "redirect:/admin/ToTypes";
    }


    @GetMapping("/typeDelete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){

        typeService.removeById(id);
        attributes.addFlashAttribute("success","删除成功");

        return "redirect:/admin/ToTypes";
    }


}
