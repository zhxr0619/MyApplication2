package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class NetworkUtils {
	public static boolean td(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);

		NetworkInfo netWifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean wifi = false;//
		if (netWifi != null) {
			wifi = netWifi.isConnectedOrConnecting();
		}
		NetworkInfo netInternet = con
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean internet = false;//
		if (netInternet != null) {
			internet = netInternet.isConnectedOrConnecting();
		}
		if (wifi || internet) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean IsHaveInternet(final Context context) {
		try {
			ConnectivityManager manger = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo info = manger.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取IP
	 * @return
	 */
/*	public static String getLocalIpAddress() {
	try {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress()) {

					return inetAddress.getHostAddress().toString();
				}
			}
		}
	} catch (SocketException ex) {
		Log.e("getLocalIpAddress", ex.toString());
	}
	return null;
}
*/
//	获取本机WIFI
	private String getLocalIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		// 获取32位整型IP地址
		int ipAddress = wifiInfo.getIpAddress();

		//返回整型地址转换成“*.*.*.*”地址
		return String.format("%d.%d.%d.%d",
				(ipAddress & 0xff), (ipAddress >> 8 & 0xff),
				(ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
	}




	//	3G网络IP
	public static String getIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String getLocalIpAddress() {
		String ipAddress = null;
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface iface : interfaces) {
				if (iface.getDisplayName().equals("eth0")) {
					List<InetAddress> addresses = Collections.list(iface.getInetAddresses());
					for (InetAddress address : addresses) {
						if (address instanceof Inet4Address) {
							ipAddress = address.getHostAddress();
						}
					}
				} else if (iface.getDisplayName().equals("wlan0")) {
					List<InetAddress> addresses = Collections.list(iface.getInetAddresses());
					for (InetAddress address : addresses) {
						if (address instanceof Inet4Address) {
							ipAddress = address.getHostAddress();
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ipAddress;
	}

}
