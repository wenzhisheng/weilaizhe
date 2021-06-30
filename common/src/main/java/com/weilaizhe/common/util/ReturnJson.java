package com.weilaizhe.common.util;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author: dameizi
 * @description: 返回JSON结果
 * @dateTime 2019-04-09 16:24
 * @className com.weilaizhe.common.utils.ReturnJson
 */
public class ReturnJson {

    private static final Logger logger = LoggerFactory.getLogger(ReturnJson.class);

    public static JSONObject callbackSuccess(JSONObject jsonObject, String TradeName, String outTradeNo){
        logger.info("{}回调结束，平台订单号：{}", TradeName, outTradeNo);
        jsonObject.put(CommonConst.CODE, "10000");
        jsonObject.put(CommonConst.MSG, "success");
        jsonObject.put(CommonConst.TRADE_NO, outTradeNo);
        return jsonObject;
    }

    public static JSONObject orderSuccess(JSONObject jsonObject, String sing, String outTradeNo){
        jsonObject.put(CommonConst.CODE, "10000");
        jsonObject.put(CommonConst.MSG, "success");
        jsonObject.put(CommonConst.SIGN, sing);
        jsonObject.put(CommonConst.OUT_TRADE_NO, outTradeNo);
        return jsonObject;
    }

    public static JSONObject isTradeType(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "40002");
        jsonObject.put(CommonConst.MSG, "Invalid trade type");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-trade-type");
        jsonObject.put(CommonConst.SUB_MSG, "通道类型无效");
        return jsonObject;
    }

    public static void invalidTatolAmount(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "40002");
        jsonObject.put(CommonConst.MSG, "The amount cannot be less than or equal to 0");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-tatol-amount");
        jsonObject.put(CommonConst.SUB_MSG, "金额不能小于等于0");
    }

    public static void invalidTradeNoRepeat(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "40002");
        jsonObject.put(CommonConst.MSG, "Repeat order number submission");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-trade-no-repeat");
        jsonObject.put(CommonConst.SUB_MSG, "订单号重复提交");
    }

    public static void invalidTradeNo(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "40002");
        jsonObject.put(CommonConst.MSG, "The order number is illegal");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-trade-no");
        jsonObject.put(CommonConst.SUB_MSG, "订单号不合法");
    }

    public static void invalidAuthCode(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "40002");
        jsonObject.put(CommonConst.MSG, "Illegal authorization code");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-auth-code");
        jsonObject.put(CommonConst.SUB_MSG, "非法的授权码");
    }

    public static void invalidTradeAmount(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "40001");
        jsonObject.put(CommonConst.MSG, "Invalid order amount");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-trade-amount");
        jsonObject.put(CommonConst.SUB_MSG, "无效的订单金额");
    }

    public static void invalidFormat(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE,"40002");
        jsonObject.put(CommonConst.MSG,"Illegal form");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-format");
        jsonObject.put(CommonConst.SUB_MSG, "非法表单");
    }

    public static void quotaDay(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "80001");
        jsonObject.put(CommonConst.MSG, "quota day used up");
        jsonObject.put(CommonConst.SUB_CODE, "invalid-auth-code");
        jsonObject.put(CommonConst.SUB_MSG, "今日额度用完");
    }

    public static void unequalAmount(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "70002");
        jsonObject.put(CommonConst.MSG, "Amount mismatch");
        jsonObject.put(CommonConst.SUB_CODE, "amount_wrong");
        jsonObject.put(CommonConst.SUB_MSG, "回调金额不匹配");
    }

    public static void callbakcSignError(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "70002");
        jsonObject.put(CommonConst.MSG, "The parameter signature is incorrect");
        jsonObject.put(CommonConst.SUB_CODE, "SIGN_ERROR");
        jsonObject.put(CommonConst.SUB_MSG, "参数签名结果不正确");
    }

    public static void platformHandlingExceptions(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "00000");
        jsonObject.put(CommonConst.MSG, "Merchant handling exception");
        jsonObject.put(CommonConst.SUB_CODE, "handling-exception");
        jsonObject.put(CommonConst.SUB_MSG, "商户处理异常");
    }

    public static void serviceCurrently(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "20000");
        jsonObject.put(CommonConst.MSG, "Service Currently Unavailable");
        jsonObject.put(CommonConst.SUB_CODE, "unknow-error");
        jsonObject.put(CommonConst.SUB_MSG, "服务器暂时不可用");
    }

    public static void serviceCurrentlyXML(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "20000");
        jsonObject.put(CommonConst.MSG, "Serialization failure");
        jsonObject.put(CommonConst.SUB_CODE, "unknow-error-xml");
        jsonObject.put(CommonConst.SUB_MSG, "XML序列化失败");
    }

    public static void orderProcessed(JSONObject jsonObject){
        jsonObject.put(CommonConst.CODE, "70002");
        jsonObject.put(CommonConst.MSG, "Merchant orders have been paid and no further operations are required");
        jsonObject.put(CommonConst.SUB_CODE, "TRADE_HAS_SUCCESS");
        jsonObject.put(CommonConst.SUB_MSG, "商户订单已支付，无需更多操作");
    }

    public static boolean paramIsEmpty(String param, JSONObject jsonObject, String code, String msg) {
        if (StringUtils.isEmpty(param)) {
            jsonObject.put(CommonConst.CODE, code);
            jsonObject.put(CommonConst.MSG, msg);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-09 14:37
     * @description: 组装返回参数
     * @param: [payOrder, merchantVO]
     * @return: net.sf.json.JSONObject
     */
    public static JSONObject getJsonObject(PaymentOrderVO payOrder, MerchantVO merchantVO) {
        // 组装返回JSON对象
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("code", "10000");
        jsonObj.put("msg", CommonConst.CALLBACK_STATUS_SUCCESS);
        jsonObj.put("trade_no", payOrder.getTradeNo());
        jsonObj.put("out_trade_no", payOrder.getOutTradeNo());
        jsonObj.put("trade_type", payOrder.getTradeTypeCode());
        jsonObj.put("trade_amount", payOrder.getTotalAmount());
        jsonObj.put("sign", SignUtil.callbackSignature(payOrder, merchantVO));
        return jsonObj;
    }

}
