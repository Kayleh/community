package kayleh.wizard.community.controller;

import kayleh.wizard.community.dto.NotificationDTO;
import kayleh.wizard.community.dto.PaginationDTO;
import kayleh.wizard.community.enums.NotificationTypeEnum;
import kayleh.wizard.community.model.User;
import kayleh.wizard.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Wizard
 * @Date: 2020/5/30 19:36
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id, Model model, HttpServletRequest request) {


        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
