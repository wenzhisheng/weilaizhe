package com.weilaizhe.common.pojo.pay;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: dameizi
 * @description: 微信商户回调
 * @dateTime 2019-04-13 23:20
 * @className com.weilaizhe.common.pojo.pay.WechatCallbackVO
 */
@XmlRootElement(name = "xml")
public class WechatCallbackVO implements Serializable {

    private static final long serialVersionUID = 3557894863437250493L;

    /** 商户订单号 */
    private String out_trade_no;
    /** 微信支付订单号 */
    private String transaction_id;
    /** 现金支付金额 */
    private BigDecimal cash_fee;
    /** 订单金额 */
    private BigDecimal total_fee;
    /** 业务结果 */
    private String result_code;
    /** 签名类型 */
    private String sign_type;
    /** 签名 */
    private String sign;
    /** 支付完成时间 */
    private String time_end;

    @Override
    public String toString() {
        return "WechatCallbackVO{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", cash_fee=" + cash_fee +
                ", total_fee=" + total_fee +
                ", result_code='" + result_code + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", sign='" + sign + '\'' +
                ", time_end='" + time_end + '\'' +
                '}';
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public BigDecimal getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(BigDecimal cash_fee) {
        this.cash_fee = cash_fee;
    }

    public BigDecimal getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(BigDecimal total_fee) {
        this.total_fee = total_fee;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
}
