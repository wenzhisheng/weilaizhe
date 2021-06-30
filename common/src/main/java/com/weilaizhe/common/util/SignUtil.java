package com.weilaizhe.common.util;

import com.weilaizhe.common.exception.DescribeException;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.order.PaymentOrderVO;
import com.weilaizhe.common.pojo.pay.PaymentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author: dameizi
 * @description: 签名工具类
 * @dateTime 2019-03-29 15:29
 * @className com.weilaizhe.common.utils.SignUtil
 */
public class SignUtil {

    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 20:36
     * @description: 回调签名
     * @param: [orderVO, key]
     * @return: java.lang.String
     */
    public static String callbackSignature(PaymentOrderVO orderVO, MerchantVO merchantVO) {
        String signJoint = "out_trade_no={0}&trade_amount={1}&trade_no={2}&key={3}";
        String signParam = MessageFormat.format(signJoint,
                orderVO.getOutTradeNo(),
                orderVO.getTotalAmount().toString(),
                orderVO.getTradeNo(),
                merchantVO.getSecretKey()
        );
        String sign = CommonUtil.md5Password(signParam).toUpperCase();
        logger.info("商户订单：{},回调签名：{}", orderVO.getOutTradeNo(), sign);
        return sign;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-07 12:11
     * @description: 请求签名
     * @param: [paymentVO, merchantVO]
     * @return: java.lang.String
     */
    public static String dataSignature(PaymentVO orderVO, MerchantVO merchantVO) {
        String signJoint = "app_id={0}&auth_code={1}&detail={2}&goods_tag={3}&notify_url={4}&out_trade_no={5}&sign_type={6}&trade_amount={7}&trade_time={8}&trade_type={9}&key={10}";
        String signParam = MessageFormat.format(signJoint,
                orderVO.getApp_id(),
                orderVO.getAuth_code(),
                orderVO.getDetail(),
                orderVO.getGoods_tag(),
                orderVO.getNotify_url(),
                orderVO.getOut_trade_no(),
                orderVO.getSign_type(),
                orderVO.getTrade_amount(),
                orderVO.getTrade_time(),
                orderVO.getTrade_type(),
                merchantVO.getSecretKey()
        );
        String sign = CommonUtil.md5Password(signParam).toUpperCase();
        logger.info("商户订单：{},请求签名：{}", orderVO.getOut_trade_no(), sign);
        return sign;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-11 15:39
     * @description: 微信商户签名
     * @param: [data, key]
     * @return: java.lang.String
     */
    public static String generateSignature(Map<String, String> data, String key) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            // 参数值为空，则不参与签名
            if (data.get(k).trim().length() > 0){
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        sb.append("key=").append(key);
        String sian = "";
        try{
            sian = MD5(sb.toString()).toUpperCase();
        }catch (Exception ex){
            throw new DescribeException("微信商户签名失败", 0);
        }
        return sian;
    }


    /**
     * 生成 MD5
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 获取当前时间戳，单位秒
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis()/1000;
    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }


}
