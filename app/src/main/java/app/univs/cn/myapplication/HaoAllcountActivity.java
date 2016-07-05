package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 好买基金  我的理财  全部理财
 */
public class HaoAllcountActivity extends Activity implements
        View.OnClickListener,XListView.IXListViewListener {

    private XListView listView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hao_allcount);

        String ipaddress=getAddress(this);

        Log.e("===",ipaddress+"");

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void init() {

        listView=(XListView)findViewById(R.id.listView);

        listView.setXListViewListener(this);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);

        View view=getLayoutInflater().inflate(R.layout.layout_header,null);

        listView.addHeaderView(view);

        HaoHoldListAdapter adapter=new HaoHoldListAdapter(this);
        List<Map<String,String>> list=new ArrayList<>();
        for (int i=0;i<15;i++){
            Map<String,String> map=new HashMap<>();
            map.put("name","基金"+i);
            map.put("code","0100"+i);
            list.add(map);

        }
        adapter.addAll(list);

        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.hao_allcount_bank_tv:

                break;

            default:
                break;
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    public class HaoHoldListAdapter extends
            ArrayAdapter<Map> {
        class ViewHolder {
            TextView mTvFundname,mTvFundcode,
                    mTrade_date,mTrade_money;
        }
        public HaoHoldListAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.listview_hao_hold_item, null);
                vh = new ViewHolder();

                vh.mTvFundname = (TextView) convertView
                        .findViewById(R.id.item_hold_fundname_tv);
                vh.mTvFundcode = (TextView) convertView
                        .findViewById(R.id.item_hold_fundcode_tv);
                vh.mTrade_date = (TextView) convertView
                        .findViewById(R.id.item_hold_income_tv);
                vh.mTrade_money = (TextView) convertView
                        .findViewById(R.id.item_hold_money_tv);

            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            Map<String,String> bean=getItem(position);
            vh.mTvFundname.setText(bean.get("name"));
            vh.mTvFundcode.setText(bean.get("code"));

            convertView.setTag(vh);
            return convertView;
        }
    }



    private static String getAddress(Context context){
        String address = null;
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (wifiManager.isWifiEnabled()) {
//          wifiManager.setWifiEnabled(true);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return intToIp(ipAddress);
        }

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
        }

        return address;
    }
    private static String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }

}
