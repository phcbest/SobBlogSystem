package nat.phc.blog.response;

/**
 * @Author: PengHaiChen
 * @Description: 这个类返回请求结果
 * @Date: Create in 19:46 2020/7/19
 */
public class ResponseResult {
    private boolean success;
    private int code;
    private String message;
    private Object Data;

    public ResponseResult(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        Data = data;
    }

    public ResponseResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
