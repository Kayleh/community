package kayleh.wizard.community.controller;

import kayleh.wizard.community.dto.CommentDTO;
import kayleh.wizard.community.dto.QuestionDTO;
import kayleh.wizard.community.enums.CommentTypeEnum;
import kayleh.wizard.community.service.CommentService;
import kayleh.wizard.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/23 15:10
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);


        //累加阅读数
        questionService.inView(id);


        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        return "question";
    }

}
