package kayleh.wizard.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Wizard
 * @Date: 2020/7/28 22:29
 */
@Controller
public class headController {

    @GetMapping("/")
    public String index(){

        return "index";

    }
}
