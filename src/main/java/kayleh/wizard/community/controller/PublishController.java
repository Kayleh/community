package kayleh.wizard.community.controller;

import kayleh.wizard.community.cache.TagCache;
import kayleh.wizard.community.dto.QuestionDTO;
import kayleh.wizard.community.model.Question;
import kayleh.wizard.community.model.User;
import kayleh.wizard.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Wizard
 * @Date: 2020/5/3 20:54
 */
@Controller
public class PublishController {

//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {


        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);

        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        //
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        //
        model.addAttribute("tags", TagCache.get());

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }


//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        //&&cookies.length!=0
//        if (cookies != null)
//            for (Cookie cookie : cookies) {
//                //字符串写在equal前面，防止空指针异常
//                if ((cookie.getName().equals("token"))) {
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//            }


        String invalidList = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalidList)){
            model.addAttribute("error","输入非法标签:"+invalidList);
            return "publish";
        }


        User user = (User) request.getSession().getAttribute("user");

//        if (user==null){
//            return "redirect:/";
//        }

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
//        question.setGmtCreate(System.currentTimeMillis());
//        question.setGmtModified(question.getGmtCreate());
        question.setId(id);
        questionService.createOrUpdate(question);
//        questionMapper.create(question);

        return "redirect:/";
    }


}
