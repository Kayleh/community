package kayleh.wizard.community.exception;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 11:34
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    //每一次调用枚举类的枚举值都相当于调用一次枚举类的构造函数

    QUESTION_NOT_FOUND("你找的问题不在了,要不要换个试试?");
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }

}
