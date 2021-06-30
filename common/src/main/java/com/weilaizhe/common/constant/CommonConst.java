package com.weilaizhe.common.constant;

/**
 * @author: dameizi
 * @description: 公共常量
 * @dateTime 2019-03-26 23:56
 * @className com.weilaizhe.common.exception.OtherReturn
 */
public class CommonConst {

    /** Http Token 安全认证 */
    public static final String AUTHORIZATION = "Authorization";
    /** 验证码存放Session */
    public static final String RANDOM_CODE_KEY= "random_code_key";
    /** 年月格式 */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";
    /** 返回错误码标识 */
    public static final String ERROR = "ERROR:{0}";
    /** 登录缓存获取失败 */
    public static final String LOGIN_CACHE_FAIL = "登录缓存获取失败";
    /** 验证码错误 */
    public static final String VERIFY_CODE_ERROR = "验证码错误";
    /** 动态口令已过期 */
    public static final String DYNAMIC_PASSWORD_PAST = "动态口令已过期";
    /** 动态口令错误 */
    public static final String DYNAMIC_PASSWORD_ERROR = "动态口令错误";
    /** 账号或密码错误 */
    public static final String ACCOUNT_PASSWORD_ERROR = "账号或密码错误";
    /** 请求路径错误 */
    public static final String REQUEST_PATH_ERROR = "请求路径错误";
    /** 参数不能为空 */
    public static final String PARAM_NOT_EMPTY = "参数不能为空";
    /** 参数异常 */
    public static final String PARAM_EXCEPTION = "参数非法";
    /** 分页参数不能为空 */
    public static final String PAGE_PARAM_NOT_EMPTY = "分页参数不能为空";
    /** 登录已过期 */
    public static final String LOGIN_PAST = "登录已过期";
    /** 设置成功 */
    public static final String SETTING_SUCCESS = "设置成功";
    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "{}登录成功";
    /** 退出成功 */
    public static final String LOGOUT_SUCCESS = "退出成功";
    /** 账号登录冻结，请联系客服 */
    public static final String ACCOUNT_LOGIN_FREEZE = "账号登录冻结，请联系客服";
    /** 账号提现冻结，请联系客服 */
    public static final String ACCOUNT_WITHDRAW_FREEZE = "账号提现冻结，请联系客服";
    /** 提现密码错误 */
    public static final String WITHDRAW_PASSWORD_ERROR = "提现密码错误";
    /** 提现密码不能为空 */
    public static final String WITHDRAW_PASSWORD_NOT_EMPTY = "提现密码不能为空";
    /** 账号已被使用 */
    public static final String ACCOUNT_IS_USE = "账号已被使用";
    /** 商户编码或账号已存在 */
    public static final String CODE_OR_ACCOUNT_IS_USE = "商户编码或账号已存在";
    /** 账号不能为空 */
    public static final String ACCOUNT_NOT_EMPTY = "账号不能为空";
    /** id不能为空 */
    public static final String ID_NOT_EMPTY = "ID不能为空";
    /** 商户ID不能为空 */
    public static final String MERCHANT_ID_NOT_EMPTY = "商户ID不能为空";
    /** 请先设置密保问题 */
    public static final String SETTING_PASSWORD_PROTECT_QNSWER = "请先设置密保问题";
    /** 密保答案错误 */
    public static final String PASSWORD_PROTECT_QNSWER_ERROR = "密保答案错误";
    /** 验证码发送失败 */
    public static final String VERIFY_CODE_SEND_FAIL = "验证码发送失败";
    /** 密码不能为空 */
    public static final String PASSWORD_NOT_EMPTY = "密码不能为空";
    /** 商户号错误 */
    public static final String MERCHANT_CODE_ERROR = "商户号错误";
    /** 验证码不能为空 */
    public static final String VERIFY_CODE_NOT_EMPTY = "验证码不能为空";
    /** 原密码输入错误 */
    public static final String OLD_PASSWORD_ERROR = "原密码输入错误";
    /** 商户备注不能超过500字 */
    public static final String MERCHANT_REMARK_SIZE = "商户备注不能超过500字";
    /** 短信验证码不能为空 */
    public static final String SMS_VERIFY_CODE_NOT_EMPTY = "短信验证码不能为空";
    /** 短信发送失败 */
    public static final String SMS_SENG_FAIL = "短信发送失败";
    /** 确认密码不能为空 */
    public static final String CONFIRM_PASSWORD_NOT_EMPTY = "确认密码不能为空";
    /** 确认密码不一致 */
    public static final String CONFIRM_PASSWORD_ERROR = "确认密码不一致";
    /** IP白名单不合法 */
    public static final String IP_ILLEGAL = "：IP白名单不合法";
    /** 账号必须是4-16位的字母数字或下划线组合 */
    public static final String MERCHANT_NAME_VERIFY = "账号必须是4-16位的字母数字或下划线组合";
    /** 密码必须是6-12位数字、字母组合，字母区分大小写 */
    public static final String MERCHANT_PASSWORD_VERIFY = "密码必须是6-18位数字与字母组合，字母区分大小写";
    /** 简单密码，提现密码 */
    public static final String PASSWORD_EASY = "必须六位数字密码";
    /** 上传的文件不能为空 */
    public static final String UPLOAD_FILE_NOT_EMPTY = "上传的文件不能为空";
    /** 图片大小最大不能超过 */
    public static final String IMAGE_MAX = "图片大小最大不能超过";
    /** 图片上传只支持后缀名为： */
    public static final String IMAGE_UPLOAD_SUFFIX = "图片上传只支持后缀名为：";
    /** Bucket不能为空： */
    public static final String BUCKET_NOT_EMPTY = "Bucket不能为空";
    /** Folder不能为空： */
    public static final String FOLDER_NOT_EMPTY = "Folder不能为空";
    /** 创建新的Bucket： */
    public static final String MKDIR_BUCKET = "创建新的Bucket：";
    /** 创建新的文件夹： */
    public static final String MKDIR_FOLDER = "创建新的文件夹：";
    /** 获取IP物理地址失败 */
    public static final String GET_IP_ADDRESS_FAIL = "获取IP物理地址失败{}";
    /** OK */
    public static final String OK = "OK";
    /** 空字符 */
    public static final String NULL_CHAR = "";
    /** 点号 */
    public static final String DOT = ".";
    /** 逗号 */
    public static final String COMMA = ",";
    /** 连字符号("-") */
    public static final String HYPHEN = "-";
    /** 下划线("_") */
    public static final String UNDERLINE = "_";
    /** 连接符号（“:”） */
    public static final String COLON = ":";
    /** 连接符号（“/”） */
    public static final String SPRIT = "/";
    /** 网址前缀（“https://”） */
    public static final String URL_HTTP = "http://";
    public static final String URL_HTTPS = "https://";
    /** OSS */
    public static final String OSS = "oss-";
    /** utf-8 */
    public static final String CODING_UTF8 = "utf-8";
    /** 管理员前缀 */
    public static final String ADMIN_PREFIX = "admin_";
    /** 代理商前缀 */
    public static final String PROXY_PREFIX = "proxy_";
    /** 错误路径 */
    public static final String ERROR_PATH = "/error";
    /** 回调成功状态 */
    public static final String CALLBACK_STATUS_SUCCESS = "TRADE_SUCCESS";
    /** 回调关闭状态 */
    public static final String CALLBACK_STATUS_CLOSED = "TRADE_CLOSED";
    /** 提现冻结 */
    public static final String WITHDRAWAL_FREEZE = "-1";
    /** 状态OK */
    public static final String STATUS_OK = "OK";
    /** 数字常量0 */
    public static final int NUMBER_0 = 0;
    /** 数字常量1 */
    public static final int NUMBER_1 = 1;
    /** 数字常量2 */
    public static final int NUMBER_2 = 2;
    /** 数字常量3 */
    public static final int NUMBER_3 = 3;
    /** 数字常量4 */
    public static final int NUMBER_4 = 4;
    /** 数字常量5 */
    public static final int NUMBER_5 = 5;
    /** 数字常量6 */
    public static final int NUMBER_6 = 6;
    /** 数字常量7 */
    public static final int NUMBER_7 = 7;
    /** 数字常量8 */
    public static final int NUMBER_8 = 8;
    /** 数字常量9 */
    public static final int NUMBER_9 = 9;
    /** 数字常量12 */
    public static final int NUMBER_12 = 12;

    // 返回码
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String SUB_CODE = "sub_code";
    public static final String SUB_MSG = "sub_msg";
    public static final String SIGN = "sign";
    public static final String TRADE_NO = "trade_no";
    public static final String OUT_TRADE_NO = "out_trade_no";

    /** 支付宝回调参数成功 */
    public static final String ALIPAY_MERCHANT_CALLBACK_SUCCESS = "TRADE_SUCCESS";
    /** 支付宝回调参数等待 */
    public static final String ALIPAY_MERCHANT_CALLBACK_WAIT = "WAIT_BUYER_PAY";
    /** 支付宝回调参数关闭 */
    public static final String ALIPAY_MERCHANT_CALLBACK_CLOSED = "TRADE_CLOSED";

    /** 支付通道支付宝商户 */
    public static final String PAYMENT_CHANNELS_ZFBSH = "ZFB_SH";
    public static final String PAYMENT_CHANNELS_ZFBSH_NAME = "支付宝商户";
    /** 支付通道微信商户 */
    public static final String PAYMENT_CHANNELS_WXSH = "WX_SH";
    public static final String PAYMENT_CHANNELS_WXSH_NAME = "微信商户";
    public static final String PAYMENT_CHANNELS_WXSH_SM = "WX_SH_SM";
    public static final String PAYMENT_CHANNELS_WXSHSM_NAME = "微信商户";
    public static final String PAYMENT_CHANNELS_WXSHH5 = "WX_SH_H5";
    public static final String PAYMENT_CHANNELS_WXSHH5_NAME = "微信商户";
    /** 支付通道支付宝商户 */
    public static final String PAYMENT_CHANNELS_ZFBGRSH = "ZFB_GRSH";
    public static final String PAYMENT_CHANNELS_ZFBGRSH_NAME = "支付宝个人商户";

    /** 参数非法 */
    public static final String PARAM_ILLEGALITY = "参数非法";
    /** 订单重复 */
    public static final String REPEAT_ORDER = "订单重复";
    /** 订单号不合法 */
    public static final String ORDER_NUMBER_ILLEGALITY = "订单号不合法";
    /** 应用ID不能为空 */
    public static final String APP_ID_NOT_EMPTY = "应用ID不能为空";
    /** 授权码不能为空 */
    public static final String AUTH_CODE_NOT_EMPTY = "授权码不能为空";
    /** 交易类型不能为空 */
    public static final String TRADE_TYPE_NOT_EMPTY = "交易类型不能为空";
    /** 交易时间不能为空 */
    public static final String TRADE_TIME_NOT_EMPTY = "交易时间不能为空";
    /** 总金额不能为空 */
    public static final String TOTAL_AMOUNT_NOT_EMPTY = "总金额不能为空";
    /** 回调地址不能为空 */
    public static final String NOTIFY_URL_NOT_EMPTY = "回调地址不能为空";
    /** 商户订单号不能为空 */
    public static final String OUT_TRADE_NO_NOT_EMPTY = "商户订单号不能为空";
    /** 商品优惠标签不能为空 */
    public static final String GOODS_TAG_NOT_EMPTY = "商品优惠标签不能为空";
    /** 商品描述不能为空 */
    public static final String DETAIL_NOT_EMPTY = "商品描述不能为空";
    /** 签名类型不能为空 */
    public static final String SIGN_TYPE_NOT_EMPTY = "签名类型不能为空";
    /** 签名不能为空 */
    public static final String SIGN_NOT_EMPTY = "签名不能为空";

}
