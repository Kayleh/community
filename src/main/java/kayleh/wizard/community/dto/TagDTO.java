package kayleh.wizard.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/28 21:12
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
