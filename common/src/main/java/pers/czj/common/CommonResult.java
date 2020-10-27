package pers.czj.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 创建在 2020/6/6 22:08
 */

public class CommonResult<T> {

    @ApiModelProperty(value = "状态码")
    private int code;

    private T data;

    private String message;

    public CommonResult() {
    }

    private CommonResult(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    private CommonResult(ResultCode resultCode,T data){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }


    /**
     * @author czj
     * 返回成功通用响应信息，无数据
     * @date 2020/6/6 22:16
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> success(){
        return success(ResultCode.SUCCESS.getMessage());
    }


    /**
     * @author czj
     * 返回成功自定义响应信息，无数据
     * @date 2020/6/6 22:16
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> success(String message){
        return success(null,message);
    }
    /**
     * @author czj
     * 返回成功通用响应数据
     * @date 2020/6/6 22:16
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(ResultCode.SUCCESS,data);
    }

    /**
     * @author czj
     * 返回成功通用响应数据,自定义响应信息
     * @date 2020/6/6 22:16
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> success(T data,String message){
        return new CommonResult<>(ResultCode.SUCCESS.getCode(),data,message);
    }


    /**
     * @author czj
     * 返回失败通用响应信息
     * @date 2020/6/6 22:19
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> failed(){
        return failed(null);
    }

    /**
     * @author czj
     * 返回失败自定义响应信息，无数据
     * @date 2020/6/6 22:19
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> failed(String message){
        return failed(null,message);
    }
    /**
     * @author czj
     * 返回失败通用响应数据
     * @date 2020/6/6 22:19
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> failed(T data){
        return new CommonResult<>(ResultCode.FAILED,data);
    }

    /**
     * @author czj
     * 返回失败通用响应数据
     * @date 2020/6/6 22:19
     * @param [data]
     * @return com.czj.common.CommonResult<T>
     */
    public static <T> CommonResult<T> failed(T data,String message){
        return new CommonResult<>(ResultCode.FAILED.getCode(),data,message);
    }




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
