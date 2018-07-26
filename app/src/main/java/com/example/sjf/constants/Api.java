package com.example.sjf.constants;

/**
 * 接口
 * Created by Administrator on 2016/9/20.
 */
public class Api {
    /**
     * 头像地址拼接
     */
    public static final String WPH_USER_IMAGE = "http://wph.ainongbaba.com/uploads/";
    /**
     * 数据库名称
     */
    public static final String DATABASE_DIRE = "";

    /**
     * 万品汇接口路径
     */
    public static final String WPH = "http://wph.ainongbaba.com/api.php/";
    /**
     * 注册
     */
    public static final String REGISTER = WPH + "user/register";

    /**
     * 登录
     */
    public static final String LOGIN = WPH + "user/login";

    /**
     * 退出登录
     */
    public static  final String OUT_LOGIN = WPH + "user/logout";
    /**
     * 短信验证接口
     */
    public static final String VERIFYCODE = WPH + "user/sendSms";
    /**
     * 找回密码
     */
    public static final String FORGOTPWD = WPH +  "user/findpwd";
    /**
     * 获取个人信息
     */
    public static final String GET_USER_INFO= WPH +"users/getUserInfo";
    /**
     * 修改个人信息
     */
    public static final String EDIT_USER_INFO = WPH +"users/editUserInfo";
    /**
     * 修改头像
     */
    public static final String USER_IOCN = WPH +"users/upUserAvatar";
    /**
     * 首页二级分类接口
     */
    public static final String HOME_LATTICE= WPH + "article/get2cate";
    /**
     * 首页咨询接口
     */
    public static final String HOME_STORE = WPH + "article/getLinkList";
    /**
     * 我的红包接口
     */
    public static final String USER_ORDER="";
    /**
     * 发布广告接口
     */
    public static final String AD_VERTISE = WPH + "ad/publish";
    /**
     * 广告分类接口
     */
    public static final String VIEW_ADS = WPH + "ad/getcate";
    /**
     * 广告列表接口
     */
    public static final String AD_LIST = WPH + "ad/getAdListBycate";
    /**
     * 广告详情接口
     */
    public static final String AD_GOODS_INFO = WPH + "ad/getAdInfoByID";
    /**
     * 发红包接口
     */
    public static final String RED_MONEY = WPH +"rp/publish";
    /**
     * 红包列表
     */
    public static final String GET_RED_MONEY = WPH +"rp/getRpList";
    /**
     * 红包详情
     */
    public static final String RED_MONEY_INFO = WPH +"rp/getRpInfoByID";
    /**
     * 抢红包
     */
    public static final String GET_MONEY_PACK = WPH +"rp/getRedpack";
    /**
     * 发出的红包
     */
    public static final String ISSUE_MONEY_PACK = WPH +"rp/getMyRpList";

}
