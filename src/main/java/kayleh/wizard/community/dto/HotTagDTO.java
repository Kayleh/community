package kayleh.wizard.community.dto;

import lombok.Data;

/**
 * @Author: Wizard
 * @Date: 2020/7/14 19:17
 */
@Data
public class HotTagDTO implements Comparable {
    private String name;
    private Integer priority;


    @Override
    public int compareTo(Object o) {
        return this.getPriority()-((HotTagDTO)o).getPriority();
    }
}
