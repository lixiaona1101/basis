package com.example.lixiaona.study;

/**
 * 常量
 */

public interface ConstantConfig {

    /**
     * SP 存储
     */
    //存储登录信息             SPUtils.getString(getContext(), SP_loginInfo, "");
    String SP_loginInfo = "loginInfo";
    //登录时选择的类型  0：业主 1：商家 3：用户       SPUtils.getString(getContext(), SP_userType, "");
    String SP_userType = "userType";
    //登录时的token           SPUtils.getString(getContext(), SP_accessToken, "");
    String SP_accessToken = "accessToken";

    /**
     * EventBus
     */
    //退出登录 发出消息
    String EB_logout = "logout";
    //修改名字 发出消息
    String EB_changeHead = "changeHead";
    //修改名字 发出消息
    String EB_changeName = "changeName";
    //修改手机号 发出消息
    String EB_changePhone = "changePhone";
    //修改手机号 发出消息
    String EB_changeData = "changeData";
    //定位成功
    String EB_location = "location";
}
