package com.weilaizhe.merchant.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.weilaizhe.common.alipaymerchant.service.IAlipayMerchantService;
import com.weilaizhe.common.alipaypersonal.service.IAlipayPersonalService;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.constant.RedisKeyConst;
import com.weilaizhe.common.constant.RocketmqConst;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.order.dao.IPaymentOrderDao;
import com.weilaizhe.common.pojo.alipaymerchant.AlipayMerchantVO;
import com.weilaizhe.common.pojo.alipaypersonal.AlipayPersonalVO;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import com.weilaizhe.common.pojo.pay.WechatOrderVO;
import com.weilaizhe.common.pojo.pay.WechatResultVO;
import com.weilaizhe.common.pojo.pay.WechatSceneVO;
import com.weilaizhe.common.pojo.wechatmerchant.WechatMerchantVO;
import com.weilaizhe.common.util.*;
import com.weilaizhe.common.wechatmerchant.service.IWechatMerchantService;
import com.weilaizhe.merchant.gateway.service.IPaymentService;
import com.weilaizhe.merchant.gatewayconfig.AlipayConfig;
import com.weilaizhe.merchant.gatewayconfig.WechatConfig;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: dameizi
 * @description: 支付业务
 * @dateTime 2019-04-04 21:32
 * @className com.weilaizhe.merchant.action.service.PaymentServiceImpl
 */
@Service
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IPaymentOrderDao iPaymentOrderDao;
    @Autowired
    private IAlipayMerchantService iAlipayMerchantService;
    @Autowired
    private IWechatMerchantService iWechatMerchantService;
    @Autowired
    private IAlipayPersonalService iAlipayPersonalService;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-04 19:16
     * @description: 支付宝个人商户支付
     * @param: [paymentVO, request, response, jsonObject]
     * @return: boolean
     */
    @Override
    public boolean alipayPersonal(PaymentVO paymentVO, HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject) {
        logger.info("支付宝个人商户支付：{}", paymentVO.toString());
        // 缓存获取商户信息，缓存没有则去数据库取
        MerchantVO merchantVO = getMerchantCache(paymentVO);
        // 签名校验
        if (signatureComparison(paymentVO, jsonObject, merchantVO)) {
            return true;
        }
        // 获取可用支付宝个人商户号（启用的、未满的、未占用的、限额内的、且最空闲的）
        AlipayPersonalVO alipayPersonalVO = iAlipayPersonalService.getIdleAlipayPersonal(paymentVO.getApp_id(), paymentVO.getTrade_type());
        if (alipayPersonalVO == null){
            ReturnJson.quotaDay(jsonObject);
            return true;
        }
        // 订单号
        String orderNumber = CommonUtil.generateOrderNo(paymentVO.getApp_id());
        // 保存订单
        PaymentOrderVO orderVO = this.insertOrder(paymentVO, merchantVO, alipayPersonalVO.getAlipayAccount(), orderNumber, CommonConst.PAYMENT_CHANNELS_ZFBGRSH_NAME);
        // 缓存订单信息15分钟有效
        this.cacheOrderAndMerchantAndRequest(merchantVO, orderVO, paymentVO);
        // 发送MQ消息队列
        String tableKey = orderVO.getTradeNo().substring(0, 6);
        int level = CommonUtil.getMqDelayLevel(merchantVO.getOrderDelayTime());
        if (RocketmqProducerUtil.sendMqAlipayPersonal(level, orderNumber, tableKey, jsonObject)) {
            return true;
        }else{
            // 更新二维码状态是否占用（0：未用 1：占用）
            alipayPersonalVO.setIsUse(1);
            iAlipayPersonalService.update(alipayPersonalVO);
            // 把二维码返回支付
            jsonObject.put("paymentQrCode", alipayPersonalVO.getTradeCodeUrl());
            logger.info("商户订单：{}，支付宝个人商户下单成功", paymentVO.getOut_trade_no());
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 22:54
     * @description: 微信商户
     * @param: [paymentVO, request, response, jsonObject]
     * @return: boolean
     */
    @Override
    public boolean wechatMerchant(PaymentVO paymentVO, HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject) {
        logger.info("微信商户支付：{}", paymentVO.toString());
        // 缓存获取商户信息，缓存没有则去数据库取
        MerchantVO merchantVO = getMerchantCache(paymentVO);
        // 签名校验
        if (signatureComparison(paymentVO, jsonObject, merchantVO)) {
            return true;
        }
        // 获取支付宝商户号（启用的、未满的、限额内的、且最空闲的）
        WechatMerchantVO wechatMerchantVO = iWechatMerchantService.getIdleWechatMerchant(paymentVO.getApp_id(), paymentVO.getTrade_type());
        if (wechatMerchantVO == null){
            ReturnJson.quotaDay(jsonObject);
            return true;
        }
        // 订单号
        String orderNumber = CommonUtil.generateOrderNo(paymentVO.getApp_id());
        // 微信金额单位为分，需要把金额乘以100
        BigDecimal totalAmount = paymentVO.getTrade_amount().multiply(new BigDecimal(100)).setScale(2 ,BigDecimal.ROUND_HALF_UP);
        totalAmount = totalAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
        // 微信下单处理
        WechatOrderVO wechatOrder = this.wechatOrderProcessing(paymentVO, request, wechatMerchantVO, orderNumber, totalAmount);
        // XML序列化
        try{
            String base64;
            String xml = XMLUtil.beanToXMLString(wechatOrder);
            WechatResultVO wechatResult = (WechatResultVO) XMLUtil.XMLStringToBean(this.postWechat(xml), WechatResultVO.class);
            // 是扫码支付还是H5支付
            if (paymentVO.getTrade_type().equals(CommonConst.PAYMENT_CHANNELS_WXSHH5)){
                base64 = MessageFormat.format("{0}?url={1}&out_trade_no={2}",
                        wechatMerchantVO.getWechatMerchantPayUrl(),
                        wechatResult.getMweb_url(),
                        wechatOrder.getOut_trade_no());
                jsonObject.put("base64", base64);
                response.sendRedirect(base64);
            }else {
                base64 = QRCodeUtil.encodeBase64(wechatResult.getCode_url());
                jsonObject.put("base64", base64);
                response.sendRedirect(base64);
            }
            // 保存订单
            PaymentOrderVO orderVO = this.insertOrder(paymentVO, merchantVO, wechatMerchantVO.getWechatMerchantCode(), orderNumber, CommonConst.PAYMENT_CHANNELS_ZFBSH_NAME);
            // 缓存订单信息15分钟有效
            this.cacheOrderAndMerchantAndRequest(merchantVO, orderVO, paymentVO);
            // 发送MQ消息队列
            if (sendMqMessage(jsonObject, merchantVO, orderNumber, orderVO)) {
                return true;
            }
            logger.info("商户订单：{}，微信商户下单成功", paymentVO.getOut_trade_no());
            return false;
        }catch (Exception ex){
            ReturnJson.serviceCurrentlyXML(jsonObject);
            logger.error("微信商户下单失败");
            return true;
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 22:45
     * @description: 支付宝商户
     * @param: [paymentVO, response, jsonObject]
     * @return: boolean
     */
    @Override
    public boolean alipayMerchant(PaymentVO paymentVO, HttpServletResponse response, JSONObject jsonObject) {
        logger.info("支付宝商户支付：{}", paymentVO.toString());
        // 缓存获取商户信息，缓存没有则去数据库取
        MerchantVO merchantVO = getMerchantCache(paymentVO);
        // 签名校验
        if (signatureComparison(paymentVO, jsonObject, merchantVO)) {
            return true;
        }
        // 获取支付宝商户号（启用的未满的限额内的且最空闲的）
        AlipayMerchantVO alipayMerchantVO = iAlipayMerchantService.getIdleAlipayMerchant(paymentVO.getApp_id(), paymentVO.getTrade_type());
        if (alipayMerchantVO == null){
            ReturnJson.quotaDay(jsonObject);
            return true;
        }
        // 订单号
        String orderNumber = CommonUtil.generateOrderNo(paymentVO.getApp_id());
        // 支付宝处理下单
        if (this.alipayProcessing(paymentVO, response, alipayMerchantVO, orderNumber, jsonObject)){
            return true;
        }
        // 保存订单
        PaymentOrderVO orderVO = this.insertOrder(paymentVO, merchantVO, alipayMerchantVO.getAlipayMerchantCode(), orderNumber, CommonConst.PAYMENT_CHANNELS_ZFBSH_NAME);
        // 缓存订单信息15分钟有效
        this.cacheOrderAndMerchantAndRequest(merchantVO, orderVO, paymentVO);
        // 发送MQ队列
        if (sendMqMessage(jsonObject, merchantVO, orderNumber, orderVO)) {
            return true;
        }
        logger.info("商户订单：{}，支付宝商户下单成功", paymentVO.getOut_trade_no());
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 16:18
     * @description: 发送MQ消息队列
     * @param: [jsonObject, merchantVO, orderNumber, orderVO]
     * @return: boolean
     */
    private boolean sendMqMessage(JSONObject jsonObject, MerchantVO merchantVO, String orderNumber, PaymentOrderVO orderVO) {
        // 支付宝微信商户发送mq
        String tableKey = orderVO.getTradeNo().substring(0, 6);
        int level = CommonUtil.getMqDelayLevel(merchantVO.getOrderDelayTime());
        if (RocketmqProducerUtil.sendMqMerchantCallback(level, orderNumber, tableKey, jsonObject)) {
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 22:58
     * @description: 签名比较
     * @param: [paymentVO, jsonObject, merchantVO]
     * @return: boolean
     */
    private boolean signatureComparison(PaymentVO paymentVO, JSONObject jsonObject, MerchantVO merchantVO) {
        if (!paymentVO.getSign().equals(SignUtil.dataSignature(paymentVO, merchantVO))){
            jsonObject.put(CommonConst.CODE, "40002");
            jsonObject.put(CommonConst.MSG, "Invalid parameter");
            jsonObject.put(CommonConst.SUB_CODE, "invalid-auth-code");
            jsonObject.put(CommonConst.SUB_MSG, "无效的签名");
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-10 22:52
     * @description: 获取商户缓存
     * @param: [paymentVO]
     * @return: com.weilaizhe.common.pojo.merchant.MerchantVO
     */
    private MerchantVO getMerchantCache(PaymentVO paymentVO) {
        RMap<String, MerchantVO> cache = redissonClient.getMap(RedisKeyConst.MERCHANT_INFO_MAP);
        MerchantVO merchantVO = new MerchantVO();
        if(cache == null || cache.size() == 0){
            merchantVO.setMerchantCode(paymentVO.getAuth_code());
            merchantVO = iMerchantService.getMerchantById(merchantVO);
        }else{
            merchantVO = cache.get(paymentVO.getAuth_code());
        }
        return merchantVO;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 17:22
     * @description: 缓存订单信息15分钟有效
     * @param: [merchantVO, orderVO]
     * @return: void
     */
    private void cacheOrderAndMerchantAndRequest(MerchantVO merchantVO, PaymentOrderVO orderVO, PaymentVO paymentVO) {
        //订单号作为缓存KEY
        String tradeNoKey = MessageFormat.format(RedisKeyConst.TRADE_NO, orderVO.getTradeNo());
        RMap cache = redissonClient.getMap(tradeNoKey);
        // 请求参数 paymentVO
        cache.put(RedisKeyConst.PAYMENY_KEY, paymentVO);
        // 请求订单 orderVO
        cache.put(RedisKeyConst.ORDER_KEY, orderVO);
        // 请求商户 merchantVO
        cache.put(RedisKeyConst.MERCHANT_KEY, merchantVO);
        // 15分钟有效
        cache.expire(15L, TimeUnit.MINUTES);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 17:04
     * @description: 保存订单
     * @param: [paymentVO, merchantVO, tradeAccount, orderNnumber, tradeTypeName]
     * @return: void
     */
    private PaymentOrderVO insertOrder(PaymentVO paymentVO, MerchantVO merchantVO, String tradeAccount, String orderNnumber, String tradeTypeName) {
        // 平台下单
        PaymentOrderVO orderVO = new PaymentOrderVO();
        // 商户ID
        orderVO.setMerchantId(merchantVO.getMerchantId());
        // 商户名称
        orderVO.setMerchantName(merchantVO.getMerchantName());
        // 平台订单号
        orderVO.setTradeNo(orderNnumber);
        // 商户订单号
        orderVO.setOutTradeNo(paymentVO.getOut_trade_no());
        // 交易类型
        orderVO.setTradeTypeCode(paymentVO.getTrade_type());
        // 交易类型名称
        orderVO.setTradeTypeName(tradeTypeName);
        // 交易账号
        orderVO.setTradeAccount(tradeAccount);
        // 回调地址
        orderVO.setNotifyUrl(paymentVO.getNotify_url());
        // 支付状态（0:等待支付 1:支付成功 2:支付超时 3:支付失败）
        orderVO.setTradeStatus(0);
        // 回调状态（0:等待回调 1:回调成功 2:回调结束 3:回调失败）
        orderVO.setCallbackStatus(0);
        // 订单金额
        orderVO.setTradeAmount(paymentVO.getTrade_amount());
        // 实付金额
        orderVO.setTotalAmount(paymentVO.getTrade_amount());
        // 订单时间
        orderVO.setGmtCreate(DateUtil.stringToDate2(paymentVO.getTrade_time()));
        iPaymentOrderDao.insertOrder(orderVO, orderVO.getSuffix());
        return orderVO;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 22:41
     * @description: 支付宝处理下单
     * @param: [paymentVO, response, alipayMerchantVO, orderNnumber, jsonObject]
     * @return: boolean
     */
    private boolean alipayProcessing(PaymentVO paymentVO, HttpServletResponse response, AlipayMerchantVO alipayMerchantVO, String orderNnumber, JSONObject jsonObject) {
        // 初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.url, alipayMerchantVO.getAlipayAppid(), alipayMerchantVO.getAlipayPrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, alipayMerchantVO.getAlipayPublicKey(), AlipayConfig.SIGNTYPE);
        // 创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl(alipayMerchantVO.getAlipayCallbackUrl());
        alipayRequest.setReturnUrl(alipayMerchantVO.getAlipayReturnUrl());
        // 设置回调参数
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderNnumber);
        model.setTotalAmount(String.valueOf(paymentVO.getTrade_amount()));
        model.setProductCode("QUICK_WAP_PAY");
        model.setBody(System.currentTimeMillis() + "");
        model.setSubject(System.currentTimeMillis() + "");
        model.setPassbackParams(alipayMerchantVO.getAlipayMerchantCode());
        alipayRequest.setBizModel(model);
        String form;
        try {
            // 调用SDK生成表单
            form  = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException ex) {
            ReturnJson.serviceCurrently(jsonObject);
            logger.error("商户订单号：{}，支付宝下单失败 {}", paymentVO.getOut_trade_no(), ex);
            return true;
        }
        try {
            // 直接将完整的表单html输出到页面
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException ex){
            ReturnJson.serviceCurrently(jsonObject);
            logger.error("商户订单号：{}，支付宝返回页输出失败", paymentVO.getOut_trade_no(), ex);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 15:54
     * @description: 微信商户请求连接
     * @param: [xml]
     * @return: java.lang.String
     */
    public String postWechat(String xml) throws Exception{
        URL url = new URL(WechatConfig.POSTWXURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        // 设置维持长连接
        conn.setRequestProperty("Connection", "Keep-Alive");
        // 设置文件字符集:
        conn.setRequestProperty("Charset", "UTF-8");
        //转换为字节数组
        byte[] data = xml.getBytes();
        // 设置文件长度
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        // 设置文件类型:
        conn.setRequestProperty("contentType", "text/xml");
        // 开始连接请求
        conn.connect();
        OutputStream out = conn.getOutputStream();
        // 写入请求的字符串
        out.write(data);
        out.flush();
        out.close();
        // 请求返回的状态
        if (conn.getResponseCode() == 200) {
            // 请求返回的数据
            InputStream in = conn.getInputStream();
            String a;
            try {
                byte[] data1 = new byte[in.available()];
                in.read(data1);
                // 转成字符串
                a = new String(data1);
                in.close();
                return a;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            logger.info("微信商户请求错误状态码：{}", conn.getResponseCode());
        }
        return null;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 16:50
     * @description: 微信下单处理
     * @param: [paymentVO, request, wechatMerchantVO, orderNumber, totalAmount]
     * @return: com.weilaizhe.common.pojo.pay.WechatOrderVO
     */
    private WechatOrderVO wechatOrderProcessing(PaymentVO paymentVO, HttpServletRequest request, WechatMerchantVO wechatMerchantVO, String orderNumber, BigDecimal totalAmount) {
        String ip = CommonUtil.getIp(request);
        ip = ip == null ? "203.90.246.240" : ip;
        WechatOrderVO wechatOrder = new WechatOrderVO();
        wechatOrder.setBody(CommonUtil.commodity(wechatMerchantVO));
        wechatOrder.setMch_id(wechatMerchantVO.getWechatMerchantCode());
        wechatOrder.setProduct_id(orderNumber);
        wechatOrder.setAppid(wechatMerchantVO.getWechatAppid());
        wechatOrder.setNotify_url(paymentVO.getNotify_url());
        wechatOrder.setOut_trade_no(orderNumber);
        wechatOrder.setSpbill_create_ip(ip);
        wechatOrder.setTotal_fee(totalAmount);
        wechatOrder.setNonce_str(CommonUtil.generateNonceStr());
        // 组装签名参数
        Map<String,String> map = new HashMap(16);
        map.put("appid",wechatOrder.getAppid());
        map.put("body",wechatOrder.getBody());
        map.put("mch_id",wechatOrder.getMch_id());
        map.put("nonce_str",wechatOrder.getNonce_str());
        map.put("notify_url",wechatOrder.getNotify_url());
        map.put("out_trade_no",wechatOrder.getOut_trade_no());
        map.put("product_id",wechatOrder.getProduct_id());
        map.put("spbill_create_ip",wechatOrder.getSpbill_create_ip());
        map.put("total_fee",wechatOrder.getTotal_fee().toString());
        map.put("trade_type",wechatOrder.getTrade_type());
        // 是扫码支付还是H5支付
        if (paymentVO.getTrade_type().equals(CommonConst.PAYMENT_CHANNELS_WXSHH5)){
            map.put("scene_info",wechatOrder.getScene_info());
            wechatOrder.setTrade_type(WechatConfig.H5TYPE);
            WechatSceneVO wechatSceneVO = new WechatSceneVO();
            WechatSceneVO.SceneH5 sceneH5 = new WechatSceneVO.SceneH5();
            sceneH5.setType("WAP");
            sceneH5.setWap_name("微信支付");
            sceneH5.setWap_url(WechatConfig.PAYURL);
            wechatSceneVO.setH5_info(sceneH5);
            JSON json = (JSON) JSON.toJSON(wechatSceneVO);
            wechatOrder.setScene_info(json.toJSONString());
        }else {
            wechatOrder.setTrade_type(WechatConfig.TRADETYPE);
        }
        String signWechat = SignUtil.generateSignature(map, WechatConfig.GETKEY);
        wechatOrder.setSign(signWechat);
        return wechatOrder;
    }

}
