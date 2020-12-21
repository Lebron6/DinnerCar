package com.resttcar.dh.entity;

/**
 * Created by James on 2020/6/9.
 */
public class CommitOrderResult {
    /**
     * code : 1
     * data : {"return_code":"SUCCESS","return_msg":"OK","appid":"wx17701a8bb1509634","mch_id":"1591085741","nonce_str":"pFfdHhs8rkxjUJhs","sign":"45A1FFCE87F88497626C75FE541A16F5","result_code":"SUCCESS","prepay_id":"wx0910520451330347f71d73971348882200","trade_type":"NATIVE","code_url":"weixin://wxpay/bizpayurl?pr=8tCosb8","order_id":"875","pay_price":0.03,"alipay_code_url":"https://qr.alipay.com/bax081769b6w0chzpaxl507d"}
     * msg : 提交成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * return_code : SUCCESS
         * return_msg : OK
         * appid : wx17701a8bb1509634
         * mch_id : 1591085741
         * nonce_str : pFfdHhs8rkxjUJhs
         * sign : 45A1FFCE87F88497626C75FE541A16F5
         * result_code : SUCCESS
         * prepay_id : wx0910520451330347f71d73971348882200
         * trade_type : NATIVE
         * code_url : weixin://wxpay/bizpayurl?pr=8tCosb8
         * order_id : 875
         * pay_price : 0.03
         * alipay_code_url : https://qr.alipay.com/bax081769b6w0chzpaxl507d
         */

        private String return_code;
        private String return_msg;
        private String appid;
        private String mch_id;
        private String nonce_str;
        private String sign;
        private String result_code;
        private String prepay_id;
        private String trade_type;
        private String code_url;
        private String order_id;
        private double pay_price;
        private String alipay_code_url;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getCode_url() {
            return code_url;
        }

        public void setCode_url(String code_url) {
            this.code_url = code_url;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public double getPay_price() {
            return pay_price;
        }

        public void setPay_price(double pay_price) {
            this.pay_price = pay_price;
        }

        public String getAlipay_code_url() {
            return alipay_code_url;
        }

        public void setAlipay_code_url(String alipay_code_url) {
            this.alipay_code_url = alipay_code_url;
        }
    }
}
