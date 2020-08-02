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

    public ResponseResult(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }


    public static ResponseResult SUCCESS(String message){
        ResponseResult responseResult = new ResponseResult(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult GET (ResponseState state){
        return new ResponseResult(state);
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(ResponseState.SUCCESS);
    }

    public static ResponseResult FAILED() {
        return new ResponseResult(ResponseState.FAILED);
    }

    public static ResponseResult FAILED(String name) {
        ResponseResult responseResult = new ResponseResult(ResponseState.FAILED);
        responseResult.setMessage(name);
        return responseResult;
    }

    public static ResponseResult ACCOUNT_NOT_LOGIN() {
        return new ResponseResult(ResponseState.ACCOUNT_NOT_LOGIN);
    }
    public static ResponseResult GET_RESOURCE_FAILED() {
        return new ResponseResult(ResponseState.GET_RESOURCE_FAILED);
    }
    public static ResponseResult PERMISSION_FORBID() {
        return new ResponseResult(ResponseState.PERMISSION_FORBID);
    }
    public static ResponseResult ACCOUNT_FORBID() {
        return new ResponseResult(ResponseState.ACCOUNT_FORBID);
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

    public ResponseResult setData(Object data) {
        Data = data;
        return this;
    }
}
