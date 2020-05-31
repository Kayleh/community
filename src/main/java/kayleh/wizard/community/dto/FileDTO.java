package kayleh.wizard.community.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Wizard
 * @Date: 2020/5/31 13:44
 */
@Data
public class FileDTO {

    private int success;
    private String message;
    private String url;
//    private Boolean success; // 响应是否成功
//    private Integer code; // 状态码
//    private String message; // 返回信息
//    private String url;
//    private Map<String, Object> data = new HashMap<>(); // 返回数据，放在键值对中

//    private fileDTO() {
//    }
//
//    /**
//     * 操作成功，调用这个方法
//     *
//     * @return 返回成功的数据
//     */
//    public static fileDTO ok() {
//        fileDTO fileDTO = new fileDTO();
//        fileDTO.setSuccess(true);
////        r.setCode(ResultCode.SUCCESS);
//        fileDTO.setMessage("操作成功");
//        return fileDTO;
//    }
//
//    /**
//     * 操作失败，调用这个方法
//     *
//     * @return 返回失败的数据
//     */
//    public static fileDTO error() {
//        fileDTO fileDTO = new fileDTO();
//        fileDTO.setSuccess(false);
////        r.setCode(ResultCode.ERROR);
//        fileDTO.setMessage("操作失败");
//        return fileDTO;
//    }
//
//
//    // 使用链式编程
//
//    public fileDTO success(Boolean success) {
//        this.setSuccess(success);
//        return this;
//    }
//
//    public fileDTO message(String message) {
//        this.setMessage(message);
//        return this;
//    }
//
//    public fileDTO code(Integer code) {
//        this.setCode(code);
//        return this;
//    }
//
//    public fileDTO data(String key, Object value) {
//        this.data.put(key, value);
//        return this;
//    }
//
//
//    public fileDTO data(Map<String, Object> map) {
//        this.setData(map);
//        return this;
//    }

}

