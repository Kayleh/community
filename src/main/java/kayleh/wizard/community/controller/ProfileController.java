package kayleh.wizard.community.controller;

import kayleh.wizard.community.dto.PaginationDTO;
import kayleh.wizard.community.model.User;
import kayleh.wizard.community.service.NotificationService;
import kayleh.wizard.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Wizard
 * @Date: 2020/5/7 12:11
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;


    @GetMapping("/home/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model, HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
//
//        User user = null;
//
//        Cookie[] cookies = request.getCookies();
//        //&&
//        if (cookies != null && cookies.length != 0) {
//            for (Cookie cookie : cookies) {
//                //字符串写在equal前面，防止空指针异常
//                if ((cookie.getName()).equals("token")) {
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");


        if (user == null) {
            return "redirect:/";
        }

        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);

            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {

            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);

//            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("pagination", paginationDTO);
            model.addAttribute("section", "replies");
//            model.addAttribute("unreadCount", unreadCount);
            model.addAttribute("sectionName", "最新回复");
        }

        //用户创建时间
        Long gmtCreate = user.getGmtCreate();
        long currentTime = System.currentTimeMillis();
        model.addAttribute("userCreateTime", currentTime - gmtCreate);

        //活跃
        long count = questionService.countByQusetion(user);
        model.addAttribute("questionCount", count);

        return "profile";
    }


}
