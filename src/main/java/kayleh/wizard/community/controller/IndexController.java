package kayleh.wizard.community.controller;

import kayleh.wizard.community.dto.QuestionDTO;
import kayleh.wizard.community.mapper.QuestionMapper;
import kayleh.wizard.community.mapper.UserMapper;
import kayleh.wizard.community.model.Question;
import kayleh.wizard.community.model.User;
import kayleh.wizard.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/4/28 17:10
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
//    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model) {
        Cookie[] cookies = request.getCookies();
        //&&
        if (cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies)
                //字符串写在equal前面，防止空指针异常
                if ((cookie.getName()).equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }//return "index";


        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
