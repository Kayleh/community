package kayleh.wizard.community.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Wizard
 * @Date: 2020/5/4 13:10
 */
@Data
public class Question {
    private Integer id;
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
