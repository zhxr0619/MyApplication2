package app.univs.cn.myapplication;

import java.io.Serializable;

/**
 * Created by zxr on 2015/11/13.
 *
 * 公共请求参数
 * ------查询基金交易持仓（HB301）
 * ------查询好买支持银行信息（HB116）
 */
public class HaoBaseRequestBean implements Serializable {

    private String corpCustNo;//	VarChar(50)	Not Null 商户客户号  商户平台客户唯一标识
    private String corpCustIP;//VarChar(50)	Not Nul  客户IP 商户平台客户IP
    private String requestTime;//	VarChar(14)	Not Null请求时间 YYYYMMddHHmmss
    private String applyId;//VarChar(36)	Not Null 请求流水号 确保每个请求流水号不重复
    private String opType;//	Char(5)	Not Null 操作类型

    public String getOpType() {
        return opType;
    }

    public String getApplyId() {
        return applyId;
    }

    public String getCorpCustNo() {
        return corpCustNo;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public String getCorpCustIP() {
        return corpCustIP;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public void setCorpCustIP(String corpCustIP) {
        this.corpCustIP = corpCustIP;
    }

    public void setCorpCustNo(String corpCustNo) {
        this.corpCustNo = corpCustNo;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
