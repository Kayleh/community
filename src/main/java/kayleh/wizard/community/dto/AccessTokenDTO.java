package kayleh.wizard.community.dto;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/4/29 11:50
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String redirect_uri;
    private String state;
    private String code;
    private String client_secret;


}
