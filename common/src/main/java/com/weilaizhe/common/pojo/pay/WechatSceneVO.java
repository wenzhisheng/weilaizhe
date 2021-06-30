package com.weilaizhe.common.pojo.pay;

/**
 * @author: dameizi
 * @description: 微信商户支付场景
 * @dateTime 2019-04-11 14:57
 * @className com.weilaizhe.common.pojo.pay.WechatScene
 */
public class WechatSceneVO {

    /** h5信息 */
    private SceneH5 h5_info;

    public SceneH5 getH5_info() {
        return h5_info;
    }

    public void setH5_info(SceneH5 h5_info) {
        this.h5_info = h5_info;
    }

    public static class SceneH5 {
        private String type;
        private String wap_url;
        private String wap_name;

        public String getType() {
            return type;
        }

        public String getWap_url() {
            return wap_url;
        }

        public String getWap_name() {
            return wap_name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setWap_url(String wap_url) {
            this.wap_url = wap_url;
        }

        public void setWap_name(String wap_name) {
            this.wap_name = wap_name;
        }
    }

}
