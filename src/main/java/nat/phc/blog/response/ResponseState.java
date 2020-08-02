package nat.phc.blog.response;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 14:37 2020/7/20
 */
public enum  ResponseState {
    /**
     *
     */
    SUCCESS(2000,"操作成功",true),
    FAILED(4000,"操作失败",false),

    JOIN_IN_SUCCESS(2008,"注册成功",true),

    GET_RESOURCE_SUCCESS(2002,"获取资源成功",true),
    GET_RESOURCE_FAILED(4002,"获取资源失败",false),

    ACCOUNT_NOT_LOGIN(4003,"账号未登录",false),
    PERMISSION_FORBID(4004,"无权操作",false),

    ACCOUNT_FORBID(4004,"用户已被封禁",false),

    LOGIN_SUCCESS(2001,"登录成功",true),
    LOGIN_FAILED(4001,"登录失败",false);




    ResponseState(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }


    private int code;
    private String message;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
