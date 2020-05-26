package kayleh.wizard.community.dto;

import kayleh.wizard.community.model.User;
import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/5/5 11:27
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private String tag;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
