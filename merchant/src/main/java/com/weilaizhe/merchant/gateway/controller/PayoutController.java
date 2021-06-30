package com.weilaizhe.merchant.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.weilaizhe.common.annotation.OtherReturn;
import com.weilaizhe.common.constant.CommonConst;
import com.weilaizhe.common.merchant.service.IMerchantService;
import com.weilaizhe.common.merchantbalance.service.IMerchantBalanceService;
import com.weilaizhe.common.order.service.IPayoutOrderService;
import com.weilaizhe.common.pojo.merchant.MerchantVO;
import com.weilaizhe.common.pojo.merchantbalance.MerchantBalanceVO;
import com.weilaizhe.common.pojo.pay.PayoutVO;
import com.weilaizhe.common.util.CommonUtil;
import com.weilaizhe.common.util.ReturnJson;
import com.weilaizhe.merchant.gateway.service.IPayoutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 支出请求
 * @dateTime 2019-04-11 21:55
 * @className com.weilaizhe.merchant.gateway.controller.PayoutController
 */
@RestController
@RequestMapping(value = "/pay")
@Api(value="PayoutController", tags="PayoutController", description="支出请求")
public class PayoutController {

    private static final Logger logger = LoggerFactory.getLogger(PayoutController.class);

    @Autowired
    private IPayoutService iPayoutService;
    @Autowired
    private IMerchantService iMerchantService;
    @Autowired
    private IPayoutOrderService iPayoutOrderService;
    @Autowired
    IMerchantBalanceService iMerchantBalanceService;

    /**
     * @author: dameizi
     * @dateTime: 2019-06-01 20:06
     * @description: 支出接口
     * @param: [payoutVO, request, response]
     * @return: java.lang.Object
     */
    @OtherReturn
    @RequestMapping(value = "/payout", method = RequestMethod.POST)
    @ApiOperation(value = "支出请求", notes = "必填参数：")
    public Object requestPayout(@RequestBody(required = false) PayoutVO payoutVO, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        // 金额校验
        if (amountVerify(payoutVO, jsonObject)){
            return jsonObject;
        }
        // 参数判空验证
        if (paramsIsEmpty(payoutVO, jsonObject)) {
            return jsonObject;
        }
        // 授权码与订单号校验，且订单不能重复提交
        if (authCodeAndOrder(payoutVO, jsonObject)) {
            return jsonObject;
        }
        logger.info("支付请求：{}", payoutVO.toString());
        // 批量代付业务
        iPayoutService.requestPayout(jsonObject, payoutVO, request, response);
        // 返回下单成功
        return ReturnJson.orderSuccess(jsonObject, payoutVO.getSign(), payoutVO.getOut_trade_no());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 22:04
     * @description: 授权码与订单号校验
     * @param: [paymentVO, jsonObject]
     * @return: boolean
     */
    private boolean authCodeAndOrder(PayoutVO payoutVO, JSONObject jsonObject) {
        // 应用ID与授权码合法验证
        if (iMerchantService.checkMerchantCode(payoutVO.getApp_id(), payoutVO.getAuth_code()) == 0 ){
            ReturnJson.invalidAuthCode(jsonObject);
            return true;
        }
        // 商户订单号校验,10-32位大小写字母、数字与下划线组合
        String regex_out_trade_no = "^[a-z0-9A-Z]\\w{8,30}[a-z0-9A-Z]$";
        if (!payoutVO.getOut_trade_no().matches(regex_out_trade_no)){
            ReturnJson.invalidTradeNo(jsonObject);
            return true;
        }
        // 订单不能重复提交
        if (iPayoutOrderService.repeatOrderSubmission(payoutVO.getOut_trade_no(), payoutVO.getAuth_code()) != 0){
            ReturnJson.invalidTradeNoRepeat(jsonObject);
            return true;
        }
        return false;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-04-08 21:50
     * @description: 金额校验
     * @param: [payoutVO, jsonObject]
     * @return: boolean
     */
    private boolean amountVerify(PayoutVO payoutVO, JSONObject jsonObject) {
        if (payoutVO == null){
            ReturnJson.invalidFormat(jsonObject);
            return true;
        }
        if (StringUtils.isEmpty(payoutVO.getTotal_amount())) {
            ReturnJson.invalidTradeAmount(jsonObject);
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
    private boolean paramsIsEmpty(PayoutVO payoutVO, JSONObject jsonObject) {
        if (ReturnJson.paramIsEmpty(String.valueOf(payoutVO.getApp_id()), jsonObject, "40001","missing-app-id")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getAuth_code(), jsonObject, "40001","missing-auth-code")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getCardholder_name(), jsonObject, "40001","missing-cardholder_name")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getBank_code(), jsonObject, "40001","missing-bank-code")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getBank_number(), jsonObject, "40001","missing-bank-number")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getTrade_time(), jsonObject, "40001","missing-timestamp")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getOut_trade_no(), jsonObject, "40001","missing-out-trade-no")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getSign_type(), jsonObject, "40001","missing-signature-type")) {
            return true;
        }
        if (ReturnJson.paramIsEmpty(payoutVO.getSign(), jsonObject, "40001","missing-signature")) {
            return true;
        }
        if (!CommonUtil.nameVerfy(payoutVO.getCardholder_name())){
            jsonObject.put(CommonConst.CODE, "40002");
            jsonObject.put(CommonConst.MSG, "Wrong format of name");
            jsonObject.put(CommonConst.SUB_CODE, "invalid-cardholder-name");
            jsonObject.put(CommonConst.SUB_MSG, "持卡人姓名格式不对");
            return true;
        }
        if (!CommonUtil.bankNumberVerfy(payoutVO.getBank_number())){
            jsonObject.put(CommonConst.CODE, "40002");
            jsonObject.put(CommonConst.MSG, "The bank card number is not in the correct format");
            jsonObject.put(CommonConst.SUB_CODE, "invalid-bank-number");
            jsonObject.put(CommonConst.SUB_MSG, "持卡人姓名格式不对");
            return true;
        }
        if (!StringUtils.isEmpty(payoutVO.getTotal_amount())) {
            if (payoutVO.getTotal_amount().compareTo(BigDecimal.ZERO)  < 1){
                ReturnJson.invalidTatolAmount(jsonObject);
                return true;
            }else{
                MerchantBalanceVO balanceVO = new MerchantBalanceVO();
                balanceVO.setMerchantCode(payoutVO.getAuth_code());
                MerchantBalanceVO merchantBalance = iMerchantBalanceService.getMerchantBalance(balanceVO);
                if (payoutVO.getTotal_amount().compareTo(merchantBalance.getBalance()) == -1){
                    jsonObject.put(CommonConst.CODE, "70003");
                    jsonObject.put(CommonConst.MSG, "Insufficient merchant balance");
                    jsonObject.put(CommonConst.SUB_CODE, "notenough");
                    jsonObject.put(CommonConst.SUB_MSG, "余额不足");
                    return true;
                }
            }
        }
        return false;
    }

}
