package com.by.dalitek.modelhouse.app;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author zhxr
 * @Description TODO
 * @Name MyApplication.java
 * @param
 * @time: 2015年1月23日 上午11:41:21
 */
public class MyApplication extends Application {

	private static MyApplication mInstance = null;
	/**
	 * UI缓存
	 */
	private List<Activity> activities;

	@Override
	public void onCreate() {
		activities = new LinkedList<Activity>();
		super.onCreate();
		mInstance = this;
	}
	/**
	 *
	 * @param @param activity
	 * @Description TODO
	 * @return void
	 */
	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	/**
	 *
	 * @param
	 * @Description TODO
	 * @return void
	 */
	public void clearActivities() {
		if (activities != null && activities.size() >= 1) {
			for (Activity activity : activities) {
				if (activity != null) {
					activity.finish();
				}
			}
		}
		activities.clear();

	}
	public static MyApplication getInstance() {
		return mInstance;
	}
}
