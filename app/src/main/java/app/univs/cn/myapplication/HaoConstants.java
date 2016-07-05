package app.univs.cn.myapplication;

/**
 * Created by zxr on 2015/11/18.
 */
public class HaoConstants {
    /**
     * 加密数据
     */
    public static final String ENCRYPT_FLAG = "encrypt";
    /**
     * 加签数据
     */
    public static final String SIGN_FLAG = "sign";

    //接口URL
    /**
     * 业务接口（测试联调）地址：
     */
    public static final String GETHAODATAURL_TEST = "http://101.231.204.242:8483/apistd/coop/service.htm";
    /**
     * 业务接口（生产）地址：
     */
    public static final String GETHAODATAURL = "https://trade.ehowbuy.com/apistd/coop/service.htm";

    /**
     * 操作类型
     */
    public static final class OpType {
        /**
         * 查询好买支持银行列表
         */
        public static final String SUPPORTBANKLIST = "HB116";
        /**
         * 查询银行省份城市信息
         */
        public static final String PROVINCECITY = "HB117";
        /**
         * 查询基金交易持仓
         */
        public static final String FUNDTRADEHOLD = "HB301";
        /**
         * 查询基金交易持仓明细
         */
        public static final String FUNDTRADEHOLDDETAIL = "HB302";
        /**
         * 查询交易记录
         */
        public static final String TRADELIST = "HB311";

    }


}
