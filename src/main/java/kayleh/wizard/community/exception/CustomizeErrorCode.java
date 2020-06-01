package kayleh.wizard.community.exception;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 11:34
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    //每一次调用枚举类的枚举值都相当于调用一次枚举类的构造函数

    QUESTION_NOT_FOUND(2001,"你找的问题不在了,要不要换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYS_ERROR(2004,"服务冒烟了，要不然你稍后再试试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND (2006,"你回复的评论不存在了"),
    CONTENT_EMPTY (2007,"输入内容为空"),
    READ_NOTIFICATION_FAIL (2008,"兄弟你在读别人的信息呢"),
    NOTIFICATION_NOT_FOUND (2009,"消息莫非是不翼而飞了?"),
    FILE_UPLOAD_FAIL(2010,"文件上传失败");
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
