package kayleh.wizard.community.enums;

/**
 * @Author: Wizard
 * @Date: 2020/5/29 21:22
 */
public enum NotificationStatusEnum {
    UN_READ(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
