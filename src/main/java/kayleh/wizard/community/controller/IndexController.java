package kayleh.wizard.community.controller;

import kayleh.wizard.community.cache.HotTagCache;
import kayleh.wizard.community.cache.TagCache;
import kayleh.wizard.community.dto.PaginationDTO;
import kayleh.wizard.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/4/28 17:10
 */
@Controller
public class IndexController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
//    @RequestMapping("/")
    public String index(//HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag
    ) {
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0) {
//            for (Cookie cookie : cookies)
//                //字符串写在equal前面，防止空指针异常
//                if ((cookie.getName()).equals("token")) {
//                    String token = cookie.getValue();
//                    User user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//        }


        PaginationDTO pagination = questionService.list(search,tag, page, size);

        List<String> tags = hotTagCache.getHots();

        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);

        model.addAttribute("alltags", TagCache.get());

        model.addAttribute("tags", tags);
        model.addAttribute("tag", tag);
        return "index";
    }
}
