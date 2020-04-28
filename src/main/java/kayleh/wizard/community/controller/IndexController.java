package kayleh.wizard.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Wizard
 * @Date: 2020/4/28 17:10
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){

        return "index";
    }

}
