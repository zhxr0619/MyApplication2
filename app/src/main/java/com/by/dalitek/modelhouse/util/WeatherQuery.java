package com.by.dalitek.modelhouse.util;

import android.util.Log;

import com.by.dalitek.modelhouse.domain.WeatherDomain;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zhxr
 * @Description 天气预报信息查询
 * @Name WeatherQuery.java
 * @param
 * @time: 2015年3月3日 下午1:52:23
 */
public class WeatherQuery {

	String TAG="WeatherQuery";

	public WeatherDomain[] getWeatherByCityId(String cityId){
		WeatherDomain[] weatherDomains = new WeatherDomain[1];
		// http://m.weather.com.cn/data/101070101.html ---日期2014年3月4号
		//新的api接口   http://m.weather.com.cn/atad/101070101.html
		String URL = "http://m.weather.com.cn/atad/" + cityId + ".html";
		String weather_result = "";
		HttpGet httpRequest = new HttpGet(URL);
		// 获得HttpResponse对象
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {//200
				// 取得返回的数据
				weather_result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			Log.i(TAG, e.toString());
			weatherDomains[0] = new WeatherDomain();

			return weatherDomains;
		}
		// 以下是对返回JSON数据的解析
		if (null != weather_result && !"".equals(weather_result)) {
			try {
				JSONObject jsonObject = new JSONObject(weather_result)
						.getJSONObject("weatherinfo");
				for (int i = 0; i < weatherDomains.length; i++) {
					WeatherDomain weatherDomain = new WeatherDomain();
					// 3个日期暂时都存放一天的
					weatherDomain.setName(jsonObject.getString("city"));//城市名稱
					weatherDomain.setDdate(jsonObject.getString("date_y"));//日期
					weatherDomain.setWeek(jsonObject.getString("week"));//星期
					weatherDomain.setTemp(jsonObject.getString("temp" + (i + 1)));//溫度
					weatherDomain.setWind(jsonObject.getString("wind" + (i + 1)));//風力
					weatherDomain.setWeather(jsonObject.getString("img_title_single"));//天氣描述     "weather" + (i + 1)

					String img_single=jsonObject.getString("img_single");
					if(img_single!=null){
						if(img_single=="1"||img_single=="2"){
							img_single="1";
						}else if(img_single=="3"||img_single=="4"||img_single=="5"){
							img_single="4";
						}else if(img_single=="0"){
							img_single="0";
						}else{
							img_single="7";
						}
					}

					weatherDomain.setImg_single(img_single);
					weatherDomains[i] = weatherDomain;
				}
			} catch (JSONException e) {
				Log.i(TAG, e.toString());
				weatherDomains[0] = new WeatherDomain();

				return weatherDomains;
			}
		}
		return weatherDomains;
	}



}
