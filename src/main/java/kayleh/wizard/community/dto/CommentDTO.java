package kayleh.wizard.community.dto;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 16:18
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
