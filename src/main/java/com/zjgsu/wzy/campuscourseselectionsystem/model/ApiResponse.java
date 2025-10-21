package com.zjgsu.wzy.campuscourseselectionsystem.model;

import java.util.Map;

public class ApiResponse {

    private boolean success;  // 请求是否成功
    private Map<String, Object> message;   // 多个键值对，存储返回的信息

    public ApiResponse(boolean success, Map<String, Object> message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    public void setMessage(Map<String, Object> message) {
        this.message= message;
    }
}
