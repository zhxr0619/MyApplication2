package app.univs.cn.myapplication;

/**
 * Created by zxr on 2015/11/13.
 * 查询交易记录(HB311)
 */
public class HaoTradeRequestBean extends HaoBaseRequestBean {
    private String beginTradeDt;//	Varchar(8) 起始日期
    private String endTradeDt;//	Varchar(8)截止日期
    private String searchKey;//	Varchar(30)基金查询关键字
    private String bankAcct;//	Varchar(30) 银行卡
    /**
     * []	订单状态 1-待确认，
     * 2-撤单，
     * 3-交易成功，
     * 4-交易失败，
     * 5-未付款，
     * 6-部分成交，
     * 7-付款中
     */
    private int[] dealStatusArr;//
    private int pageNo;//	int	 第几页 0表示不分页
    private int pageSize;// int 每页数量 	0 表示不限数量

    public String getBeginTradeDt() {
        return beginTradeDt;
    }

    public void setBeginTradeDt(String beginTradeDt) {
        this.beginTradeDt = beginTradeDt;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getEndTradeDt() {
        return endTradeDt;
    }

    public void setEndTradeDt(String endTradeDt) {
        this.endTradeDt = endTradeDt;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public int[] getDealStatusArr() {
        return dealStatusArr;
    }

    public void setDealStatusArr(int[] dealStatusArr) {
        this.dealStatusArr = dealStatusArr;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
