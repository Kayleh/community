package kayleh.wizard.community.provider;

import com.alibaba.fastjson.JSON;
import kayleh.wizard.community.dto.AccessTokenDTO;
import kayleh.wizard.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @Author: Wizard
 * @Date: 2020/4/29 11:46
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
//            String[] split = response.body().string().split("&");
//            return split[0].split("=")[1];
//            System.out.println(string);
//            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
//            e.printStackTrace();
        }return null;

    }

}
