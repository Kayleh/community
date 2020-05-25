package kayleh.wizard.community.exception;

/**
 * @Author: Wizard
 * @Date: 2020/5/25 11:06
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message =errorCode.getMessage();
    }
    @Override
    public String getMessage() {
        return message;
    }
}
