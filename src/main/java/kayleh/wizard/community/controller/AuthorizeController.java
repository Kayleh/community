package kayleh.wizard.community.controller;

import kayleh.wizard.community.dto.AccessTokenDTO;
import kayleh.wizard.community.dto.GithubUser;
import kayleh.wizard.community.mapper.UserMapper;
import kayleh.wizard.community.model.User;
import kayleh.wizard.community.provider.GithubProvider;
import kayleh.wizard.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.util.UUID;

/**
 * @Author: Wizard
 * @Date: 2020/4/29 10:23
 */

@Controller
public class AuthorizeController {
    //autowird
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserService userService;


    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


//    @RequestMapping("/callback")
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null && githubUser.getId()!=null) {
            User user = new User();
            //token是需要更新的
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            //accountId是唯一的
            user.setAccountId(String.valueOf(githubUser.getId()));
//            user.setGmtCreate(System.currentTimeMillis());
//            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
//            userMapper.insert(user);
            System.out.println(githubUser.getName());

            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
            //登录成功，写cookie和session 数据库的写入就代表存储到了Session

            request.getSession().setAttribute("user", githubUser);

            return "redirect:/";

        } else {
            //登录失败，重新登录
//            return "index";
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
