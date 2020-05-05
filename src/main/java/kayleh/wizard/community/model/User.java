package kayleh.wizard.community.model;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/5/2 20:51
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
