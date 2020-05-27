package kayleh.wizard.community.dto;

import kayleh.wizard.community.model.User;
import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/5/26 20:51
 */
@Data
public class CommentDTO {
    
    private Long id;


    private Long parentId;


    private Integer type;


    private Long commentator;

    private Long gmtCreate;


    private Long gmtModified;


    private Long likeCount;


    private String content;

    private User user;
}
