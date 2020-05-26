package kayleh.wizard.community.enums;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 18:00
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);


    private Integer type;


    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType()==type){
                return true;
            }
        }
        return false;
    }

}
