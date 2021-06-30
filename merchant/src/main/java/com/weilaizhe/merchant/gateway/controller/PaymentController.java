package com.weilaizhe.merchant.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.annotation.OtherReturn;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.order.service.IPaymentOrderService;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.pojo.tradetype.TradeTypeVO;
import com.weilaizhe.common.util.DateUtil;
import com.weilaizhe.common.util.ReturnJson;
import com.weilaizhe.merchant.gateway.service.IPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 支付请求
 * @dateTime 2019-04-02 23:51
 * @className com.weilaizhe.merchant.pay.controller.PayController
 */
@RestController
@RequestMapping(value = "/gateway")
@Api(value="PaymentController", tags="Payment", description="支付请求")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IPaymentService iPaymentService;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IPaymentOrderService iPaymentOrderService;

    /**
     * @author: dameizi
     * @dateTime: 2019-04-06 12:29
     * @description: 支付请求
     * @param: [paymentVO, request, response]
     * @return: java.lang.Object
     */
    @CrossOrigin//请求跨域
    @OtherReturn//自定义返回
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    @ApiOperation(value = "支付请求", notes = "必填参数：应用ID、授权码、交易类型、交易时间、支付金额、回调地址、商户订单号、优惠标记、商品描述、签名类型、签名")
    public Object requestPayment(HttpServletRequest request, HttpServletResponse response, @RequestBody(required = false) PaymentVO paymentVO){
        JSONObject jsonObject = new JSONObject();
        // 参数判空验证
        if (this.paramsIsEmpty(paymentVO, jsonObject)) {
            return jsonObject;
        }
        // 授权码与订单号校验，且订单不能重复提交
        if (this.authCodeAndOrder(paymentVO, jsonObject)) {
            return jsonObject;
        }
        logger.info("支付请求：{}", paymentVO.toString());
        // 根据交易类型走不同通道
        if (CommonConst.PAYMENT_CHANNELS_ZFBSH.equals(paymentVO.getTrade_type())){
            // 支付宝商户支付
            if (iPaymentService.alipayMerchant(paymentVO, response, jsonObject)){
                return jsonObject;
            }
        }else if (CommonConst.PAYMENT_CHANNELS_WXSH.equals(paymentVO.getTrade_type())){
            // 微信商户支付
            if (iPaymentService.wechatMerchant(paymentVO, request, response, jsonObject)){
                return jsonObject;
            }
        }else if (CommonConst.PAYMENT_CHANNELS_ZFBGRSH.equals(paymentVO.getTrade_type())){
            // 支付宝个人商户支付
            if (alipayPersonalPay(request, response, paymentVO, jsonObject)) {
                return jsonObject;
            }
        }else{
            return ReturnJson.isTradeType(jsonObject);
        }
        // 返回下单成功
        return ReturnJson.orderSuccess(jsonObject, paymentVO.getSign(), paymentVO.getOut_trade_no());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 21:13
     * @description: 支付宝个人商户支付
     * @param: [request, response, paymentVO, jsonObject]
     * @return: boolean
     */
    private boolean alipayPersonalPay(HttpServletRequest request, HttpServletResponse response, PaymentVO paymentVO, JSONObject jsonObject) {
        // 支付宝个人商户支付
        String alipayPersonalLockKey = MessageFormat.format(RedisKeyConst.ALIPAY_PERSONAL_MERCHANT, paymentVO.getAuth_code());
        RLock fairLock = null;
        try {
            fairLock = redissonClient.getFairLock(alipayPersonalLockKey);
            fairLock.lock(30L, TimeUnit.SECONDS);
            if (iPaymentService .alipayPersonal(paymentVO, request, response, jsonObject)){
                return true;
            }else{
                String paymentQrCode = jsonObject.getString("paymentQrCode");
                response.sendRedirect(paymentQrCode);
            }
        }catch (IOException e){
            logger.error("支付宝个人商户支付重定向失败");
        }finally {
            if (fairLock != null){
                fairLock.unlock();
            }
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 22:04
     * @description: 授权码与订单号校验
     * @param: [paymentVO, jsonObject]
     * @return: boolean
     */
    private boolean authCodeAndOrder(PaymentVO paymentVO, JSONObject jsonObject) {
        // 应用ID与授权码合法验证
        if (iMerchantService.checkMerchantCode(paymentVO.getApp_id(), paymentVO.getAuth_code()) == 0 ){
            ReturnJson.invalidAuthCode(jsonObject);
            return true;
        }
        // 商户订单号校验,10-32位大小写字母、数字与下划线组合
        String regex_out_trade_no = "^[a-z0-9A-Z]\\w{8,30}[a-z0-9A-Z]$";
        if (!paymentVO.getOut_trade_no().matches(regex_out_trade_no)){
            ReturnJson.invalidTradeNo(jsonObject);
            return true;
        }
        // 订单不能重复提交
        if (iPaymentOrderService.repeatOrderSubmission(paymentVO.getOut_trade_no(), paymentVO.getApp_id()) != 0){
            ReturnJson.invalidTradeNoRepeat(jsonObject);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 21:51
     * @description: 参数判空验证
     * @param: [paymentVO, jsonObject]
     * @return: boolean
     */
    private boolean paramsIsEmpty(PaymentVO paymentVO, JSONObject jsonObject) {
        // 对象判空
        if (paymentVO == null){
            ReturnJson.invalidFormat(jsonObject);
            return true;
        }
        if (ReturnJson.paramIsEmpty(String.valueOf(paymentVO.getApp_id()), jsonObject, "40001","missing-app-id")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getAuth_code(), jsonObject, "40001","missing-auth-code")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getTrade_type(), jsonObject, "40001","missing-trade-type")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getTrade_time(), jsonObject, "40001","missing-timestamp")) {
            try {
                DateUtil.stringToDate1(paymentVO.getTrade_time());
            }catch (ParseException e){
                return false;
            }
            return true;
        }
        // 参数相等返回0，小于参数返回-1，大于参数返回1
        if (paymentVO.getTrade_amount().compareTo(BigDecimal.ZERO)  < -1){
            ReturnJson.invalidTatolAmount(jsonObject);
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getNotify_url(), jsonObject, "40001","missing-notify-url")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getOut_trade_no(), jsonObject, "40001","missing-out-trade-no")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getGoods_tag(), jsonObject, "40001","missing-goods-tag")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getDetail(), jsonObject, "40001","missing-detail")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getSign_type(), jsonObject, "40001","missing-signature-type")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(paymentVO.getSign(), jsonObject, "40001","missing-signature")) {
            return true;
        }
        try{
            // 缓存获取支付类型缓存单笔限额范围
            RMap<String, TradeTypeVO> tradeTypeMap = redissonClient.getMap(RedisKeyConst.TRADETYPE_INFO_MAP);
            TradeTypeVO tradeType = tradeTypeMap.get(paymentVO.getTrade_type());
            String singleLimit = tradeType.getSingleLimit();
            if (singleLimit != null){
                String[] split = singleLimit.split("-");
                if (new BigDecimal(split[0]).compareTo(paymentVO.getTrade_amount()) > -1 &&
                        new BigDecimal(split[1]).compareTo(paymentVO.getTrade_amount()) < 1){
                    jsonObject.put(CommonConst.CODE, "40002");
                    jsonObject.put(CommonConst.MSG, "The amount is not subject to limitation");
                    jsonObject.put(CommonConst.SUB_CODE, "Invalid-tatol-amount-no-scope");
                    jsonObject.put(CommonConst.SUB_MSG, "金额不在限额范围内");
                    return true;
                }
            }
        }catch (Exception ex){
            jsonObject.put(CommonConst.CODE, "20000");
            jsonObject.put(CommonConst.MSG, "Service Currently Unavailable");
            jsonObject.put(CommonConst.SUB_CODE, "unknow-error");
            jsonObject.put(CommonConst.SUB_MSG, "系统繁忙");
            logger.error("交易类型缓存获取空，刷新交易类型或Redis挂掉");
            return true;
        }
        return false;
    }

}
