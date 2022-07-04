package com.tian.serverapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 消息枚举类
 * @Author QiGuang
 * @Date 2022/6/13
 * @Version 1.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum  RespBeanEnum {
    SUCCESS(200,""),
    CREATE_USER_SUCCESS(200,"用户创建成功"),
    UPDATE_USER_SUCCESS(200,"用户信息修改成功"),
    FILE_UPLOAD_SUCCESS(200,"头像更新成功"),
    ADD_FRIEND_SUCCESS(200,"添加好友成功"),
    DELETE_FRIEND_SUCCESS(200,"删除好友成功"),
    COMMENT_SUCCESS(200,"评论发表成功"),
    LOGOUT_SUCCESS(200,"退出登录成功"),
    ADD_COLLECTION_SUCCESS(200,"收藏成功"),
    DELETE_COLLECTION_SUCCESS(200,"已取消收藏"),
    ERROR(500,"服务器异常"),
    NO_ITEMS(500100,"id不存在"),
    LOGIN_ERROR(500210,"用户名或密码不正确"),
    EMPTY_USERNAME(500211,"用户名不能为空"),
    CAPTCHA_ERROR(500214,"验证码错误"),
    NO_PERMISSION(500212,"您的权限不足"),
    NOT_LOGGED_IN(500216,"未登录或登录信息已过期"),
    INVALID_INFO(500216,"登录信息已过期"),
    TOKEN_ERROR(500216,"token错误"),
    DECODE_ERROR(500217,"密码解密失败"),
    ENCODE_ERROR(500218,"密码加密失败"),
    BIND_ERROR(500219,"参数校验异常"),
    EMAIL_EXIST_ERROR(500220,"邮箱已被注册"),
    EMAIL_CODE_ERROR(500221,"邮箱验证码错误"),
    VALIDATION_ERROR(500222,"自定义参数校验器异常"),
    PASSWORD_ERROR(500223,"密码错误"),
    CREATE_USER_FAIL(500300,"用户创建失败"),
    UPDATE_USER_FAIL(500301,"用户信息修改失败"),
    FILE_UPLOAD_ERROR(500400,"文件上传失败"),
    FRIEND_NOT_EXIT(500401,"要操作的好友不存在"),
    FRIEND_ALREADY_EXIT(500402,"已添加过好友"),
    NOT_FRIEND(500403,"你们并不是好友"),
    ADD_FRIEND_FAIL(500403,"添加好友失败"),
    COMMENT_FAIL(500404,"评论发表失败"),
    NOT_COLLECTED(500405,"您未收藏过该节目"),
    ALREADY_COLLECTED(500406,"您已收藏该节目"),
    ADD_COLLECTION_FAIL(500407,"添加收藏失败"),
    DELETE_COLLECTION_FAIL(500407,"取消收藏失败"),
    REDIS_NULL(500500,"Redis中没有您想要的值"),
    SEND_MESSAGE_ERROR(500501,"消息发送失败"),
    WEBSOCKET_ERROR(500502,"websocket出现了一点问题"),
    HYSTRIX_ERROR(500600,"cloud生产者响应超时");

    private final int code;
    private final String message;
}
