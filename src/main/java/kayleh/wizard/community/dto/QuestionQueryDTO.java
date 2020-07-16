package kayleh.wizard.community.dto;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/6/1 15:09
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
    private Integer zero;// 1表示选择
}
