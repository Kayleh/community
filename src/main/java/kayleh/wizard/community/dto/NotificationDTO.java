package kayleh.wizard.community.dto;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/5/30 14:21
 */
@Data
public class NotificationDTO {

    private Long id;

    private Long gmtCreate;

    private Integer status;

    private String notifierName;
    private Long notifier;

    private String outerTitle;

    private String typeName;

    private Integer type;

    private Long outerid;



}
