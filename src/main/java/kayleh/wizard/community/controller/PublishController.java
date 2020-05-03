package kayleh.wizard.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Wizard
 * @Date: 2020/5/3 20:54
 */
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        return "public";
    }
}
