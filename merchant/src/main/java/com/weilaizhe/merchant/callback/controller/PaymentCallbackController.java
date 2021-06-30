package com.weilaizhe.merchant.callback.controller;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.annotation.OtherReturn;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.pay.WechatCallbackVO;
import com.weilaizhe.common.util.ReturnJson;
import com.weilaizhe.common.util.XMLUtil;
import com.weilaizhe.merchant.callback.service.IPaymentCallbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 支付回调
 * @dateTime 2019-04-04 21:34
 * @className com.weilaizhe.merchant.callback.controller.PaymentCallbackController
 */
@RestController
@RequestMapping(value = "/callback")
@Api(value="PaymentCallbackController", tags="PaymentCallbackController", description="支付回调")
public class PaymentCallbackController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentCallbackController.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IPaymentCallbackService iPaymentCallbackService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-17 12:11
     * @description: 手动回调
     * @param: [paymentOrderVO]
     * @return: java.lang.Object
     */
    @OtherReturn
    @PostMapping("/manualWork")
    @ApiOperation(value = "手动回调", notes = "POST请求")
    public Object manualWorkCallback(@RequestBody(required = false) PaymentOrderVO paymentOrderVO){
        JSONObject jsonObject = new JSONObject();
        if (paymentOrderVO == null){
            jsonObject.put("code","40002");
            jsonObject.put("msg","invalid-format");
            return jsonObject;
        }
        if (!iPaymentCallbackService.manualWorkCallback(paymentOrderVO, jsonObject)){
            return jsonObject;
        }
        return ReturnJson.callbackSuccess(jsonObject, paymentOrderVO.getTradeTypeName(), paymentOrderVO.getTradeNo());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-15 11:43
     * @description: 微信商户回调
     * @param: [param]
     * @return: java.lang.Object
     */
    @OtherReturn
    @PostMapping("/wxsh")
    @ApiOperation(value = "微信商户回调", notes = "POST请求")
    public Object callbackWechatMerchant(@RequestBody (required = false) String param){
        JSONObject jsonObject = new JSONObject();
        logger.info("微信商户回调开始");
        if (StringUtil.isEmpty(param)){
            jsonObject.put("code","20000");
            jsonObject.put("msg","fail");
            return jsonObject;
        }
        WechatCallbackVO wechatCallback;
        try {
            wechatCallback = (WechatCallbackVO) XMLUtil.XMLStringToBean(param, WechatCallbackVO.class);
            logger.info("微信商户回调参数：{}", wechatCallback.toString());
            RLock rLock = null;
            try {
                //避免并发睡眠2秒
                rLock = redissonClient.getFairLock(wechatCallback.getOut_trade_no());
                rLock.lock(2, TimeUnit.SECONDS);
                // 回调逻辑处理
                if (!iPaymentCallbackService.callbackWechatMerchant(wechatCallback, jsonObject)){
                    return jsonObject;
                }
            }finally {
                if(null != rLock){
                    rLock.unlock();
                }
            }
        }catch (Exception ex){
            jsonObject.put("code","20000");
            jsonObject.put("msg","fail");
            return jsonObject;
        }
        return ReturnJson.callbackSuccess(jsonObject, "微信商户", wechatCallback.getOut_trade_no());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 17:42
     * @description: 支付宝商户回调
     * @param: [request]
     * @return: java.lang.Object
     */
    @OtherReturn
    @PostMapping("/zfbsh")
    @ApiOperation(value = "支付宝商户回调", notes = "POST请求")
    public Object callbackAliPay(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        logger.info("支付宝商户回调开始");
        // 获取支付宝商户POST过来返回信息
        Map<String, String> params = getAlipayMerchantParam(request);
        if(params.isEmpty()) {
            jsonObject.put(CommonConst.CODE, "60001");
            jsonObject.put(CommonConst.MSG, "fail");
            logger.error("支付宝商户通知请求参数为空");
            return jsonObject;
        }
        // 数据验证
        if (!iPaymentCallbackService.alipayMerchantIsSuccess(params, jsonObject)){
            return jsonObject;
        }
        return ReturnJson.callbackSuccess(jsonObject, "支付宝商户", params.get("out_trade_no"));
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 11:40
     * @description: 获取支付宝商户POST过来返回信息
     * @param: [request]
     * @return: java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, String> getAlipayMerchantParam(HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>(16);
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝商户通知请求数据：{}", params);
        return params;
    }

}
