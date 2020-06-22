package com.cqrd.five.ex;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 响应结果状态枚举类
 * @author shkstart
 * @create 2019-11-17 14:57
 */
@NoArgsConstructor
@AllArgsConstructor
public enum  ResultStatusEnum {
    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功！"),

    /**
     * 密码错误
     */
    PASSWORD_NOT_MATCHING(400, "密码错误"),

    /**
     *4000-用户名冲突异常，例如：注册时，用户名已经被占用
     */
    UsernameDuplicateException(4000,"用户名冲突异常"),

    /**
     * 4001-用户数据不存在
     */
    UserNotFoundException(4001,"用户数据不存在"),

    /**
     *4002-验证密码失败
     */
    NotMatchException(4002,"用户名或密码错误"),

    /**
     * 4003-用户名和邮箱不匹配
     */
    UserNameAndEmailUnmatched(4003,"用户名和邮箱不匹配"),

    /**
     * 4004-更新数据库信息失败
     */
    Not_Update_Database(4004,"更新数据库信息失败"),

    /**
     * 4005-验证码过期
     */
    Code_Stale_Dated(4005,"验证码已过期"),

    /**
     * 4006-验证码不正确
     */
    Code_Untrue(4006,"验证码不正确"),

    /**
     * 4007-清空验证码失败
     */
    NotFailed_Code(4007,"清空验证码失败"),

    /**
     * 4008-删除用户数据失败
     */
    Delete_User_failed(4008,"删除用户数据失败"),

    /**
     * 4009-添加用户数据失败
     */
    Failed_To_Add_User(4009,"添加用户数据失败"),

    /**
     * 4010-查询用户数据失败
     */
    Query_Data_Failed(4010,"查询用户数据失败"),

    /**
     * 4011-显示用户数据失败
     */
    Show_Data_Failed(4011,"显示用户数据失败"),

    /**
     * 4012-修改用户数据失败
     */
    Failed_To_Modify_User(4012,"修改用户数据失败"),

    /**
     * 4013-上传文件格式不正确
     */
    Uploaded_File_Incorrect(4013,"上传文件格式不正确"),

    /**
     * 4014-用户名请设为文本格式
     */
    User_Name_AS_Text(4014,"用户名请设为文本格式"),

    /**
     * 4015-用户名未填写
     */
    User_name_is_not_filled_in(4015,"用户名未填写"),

    /**
     * 4016-邮箱未填写
     */
    Email_not_filled(4016,"邮箱未填写"),

    /**
     * 4017-电话未填写
     */
    Phone_not_filled(4017,"电话未填写"),

    /**
     * 4018-gitlab账号未填写
     */
    Gitlab_account_not_filled(4018,"gitlab账号未填写"),

    /**
     * 4019-成绩未填写
     */
    Score_not_filled(4019,"成绩未填写"),

    /**
     * 4020-学分未填写
     */
    Credits_not_filled(4020,"学分未填写"),

    /**
     * 4021-获取验证码失败
     */
    Failed_to_get_verification_code(4021,"获取验证码失败"),

    /**
     * 4022-验证码系统错误，请联系管理员
     */
    Verification_code_system_error(4022,"验证码系统错误，请联系管理员"),

    /**
     * 4023-导入失败
     */
    Import_failed(4023,"导入失败");


    private int code;
    private String message;

    ResultStatusEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
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
}
