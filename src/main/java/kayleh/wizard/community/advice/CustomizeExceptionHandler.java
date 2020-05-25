package kayleh.wizard.community.advice;

import kayleh.wizard.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 10:33
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model){
//        HttpStatus status = getStatus(request);

        if (ex instanceof CustomizeException){
            model.addAttribute("message",ex.getMessage());
        }else {
            model.addAttribute("message","服务冒烟了，要不然你稍后再试试");
        }


        return new ModelAndView("error");
    }
//
//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }else {
//            return HttpStatus.valueOf(statusCode);
//        }
//    }
}
