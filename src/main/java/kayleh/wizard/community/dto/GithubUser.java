package kayleh.wizard.community.dto;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/4/29 13:14
 */
@Data
public class GithubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
