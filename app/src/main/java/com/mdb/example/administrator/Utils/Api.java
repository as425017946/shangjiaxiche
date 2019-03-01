package com.mdb.example.administrator.Utils;

/**
 * 接口
 */
public class Api {
//    public static String apiUrl = "http://192.168.1.107:8080/";
    public static String ossUrl = "https://washcarimg.oss-cn-beijing.aliyuncs.com/";
    public static String apiUrl = "http://39.107.70.80:8080/";
    //登录
    public static String login = apiUrl+"washcar/merchant/shopLogin";
    //注册
    public static String zhuce = apiUrl+"washcar/merchant/addShop";
    //获取验证码
    public static String huoqusms =  apiUrl+"washcar/user/getSms";
    //验证验证码
    public static String yanzhengsms = apiUrl+"washcar/user/checkSms";
    //修改密码
    public static String modifypassword = apiUrl+"washcar/merchant/updateShopPassword";
    //短信验证码登录
    public static String yzmSms = apiUrl+"washcar/merchant/shopSmsLogin";
    //店铺资质信息上传
    public static String zizhiup = apiUrl+"washcar/merchant/updateShop";
    //绑定银行卡
    public static String BingBank = apiUrl+"washcar/merchant/updateShopCard";
    //查询银行卡
    public static String chaxunBank = apiUrl+"washcar/merchant/getShopCard";
    //发布信息
    public static String fabuxinxi = apiUrl+"washcar/merchant/addServices";
    //修改商家发布信息
    public static String upfabuxinxi = apiUrl+"washcar/merchant/updateServices";
    //查询店铺优惠券
    public static String chaxunyouhuiquan = apiUrl+"washcar/merchant/getShopCoupon";
    //添加优惠券
    public static String addyouhuiquan = apiUrl+"washcar/merchant/addShopCoupon";
    //查询店铺预约订单
    public static String chaxunorderyuyue = apiUrl+"washcar/merchant/getShopForwardOrder";
    //查询订单详情
    public static String chaxunordermore = apiUrl+"washcar/merchant/getShopForwardOrderDetail";
    //查询商家服务列表
    public static String chaxunstatus = apiUrl+"washcar/merchant/getServices";
    //修改商家服务状态
    public static String updatestatus = apiUrl+"washcar/merchant/updateServicesSwitch";
    //查询店家全部订单
    public static String chaxunAllorders = apiUrl+"washcar/merchant/getShopOrder";
    //查询商家信息
    public static String selectshopinfo = apiUrl+"washcar/merchant/getMerchant";
    //查询商家资质
    public static String selectshopzizhi = apiUrl+"washcar/merchant/getMerchantAptitude";
    //查询商家收益
    public static String selectshopshouyi = apiUrl+"washcar/merchant/getShopEarnings";
    // 查询商家总收益
    public static String selectzongshouyi = apiUrl+"washcar/merchant/getShopSumEarnings";
    //查看店铺评价
    public static String selectpingjia = apiUrl+"washcar/adminShop/getCommentByShop";
    //查询商家总评分
    public static String selectpingfen = apiUrl+"washcar/adminShop/getShopCommentGrade";
    //更换商家手机
    public static String genghuanphone = apiUrl+"washcar/merchant/updateShopMobile";
    //查询当前号码及排队人数
    public static String selectjiaohao = apiUrl+"washcar/user/getShopWaitNum";
    //叫号下一辆
    public static String xiayiliang = apiUrl+"washcar/user/updateNo";
    //获取消息列表
    public static String messages = apiUrl+"washcar/merchant/getShopPlatformInfo";
    //删除消息
    public static String deletemessages = apiUrl+"washcar/merchant/deleteShopPlatformInfo";
    //修改商家状态
    public static String xiugaishopstatus = apiUrl+"washcar/merchant/updateShopForwardOrder";
    //修改商家开启关闭叫号功能
    public static String kaiOguanbi = apiUrl+"washcar/merchant/updateWaitflag";
    //查询商家服务详情
    public static String servicemore = apiUrl+"washcar/merchant/getServicesDetail";
    //修改商家服务管理界面使用接口
    public static String upfuwuguanli = apiUrl+"washcar/merchant/updateServicesStatus";

}
