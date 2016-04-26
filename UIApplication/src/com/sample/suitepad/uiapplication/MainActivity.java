package com.sample.suitepad.uiapplication;

import java.util.ArrayList;
import java.util.List;

import com.example.uiapplication.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

/**
 * Main Activity
 * @author Ashish
 *
 */
public class MainActivity extends Activity 
{
	ArrayList<String> itemList = new ArrayList<String>();
	ArrayList<String> menuItems = new ArrayList<String>();
	ArrayList<String> priceItems = new ArrayList<String>();
	Context context;
	FragmentManager mFragmentManager;
	Intent explicitIntent = new Intent();

	int flag = 0;

	String mType;
	BroadcastReceiver mBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_main);
		itemList.add("Appetizers");
		itemList.add("Main Course");
		itemList.add("Drinks");
		itemList.add("All Items");
		ItemFragment itemFragment = new ItemFragment(itemList);
		mFragmentManager  = getFragmentManager();
		if (savedInstanceState == null) 
		{
			mFragmentManager.beginTransaction()
			.add(R.id.container, itemFragment)
			.commit();
		}
		final Intent mIntent = new Intent();
		mIntent.setAction("com.suitepad.startSupportService");
		
		/**
		 * Continue to check if service is installed. Thread exits when service is installed i.e. when a receiver is found for intent.
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				PackageManager pm = context.getPackageManager();
				List<ResolveInfo> resolveInfoList = pm.queryIntentServices(mIntent, 0);

				while (resolveInfoList == null || resolveInfoList.size() != 1) 
				{
					resolveInfoList = pm.queryIntentServices(mIntent, 0);
				}
				flag = 1;
				ResolveInfo serviceInfo = resolveInfoList.get(0);
				ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
				explicitIntent = new Intent(mIntent);
				explicitIntent.setComponent(component);
				startApp();
			}
		}).start();

	}

	/** 
	 * Function is called only when a receiver is found for intent i.e. when Support Service is installed
	 */
	private void startApp()
	{
		if(flag == 1)
		{
			startService(explicitIntent);
			mBroadcastReceiver = new BroadcastReceiver() 
			{

				@Override
				public void onReceive(Context arg0, Intent intent) 
				{
					if(intent.getAction().equals("com.suitepad.menuitem"))
					{
						menuItems = intent.getStringArrayListExtra("item list");
						priceItems = intent.getStringArrayListExtra("price");
						switchFragment();


					}

				}
			};
			ItemFragment itemFragment = new ItemFragment(itemList);
			mFragmentManager.beginTransaction()
			.replace(R.id.container, itemFragment)
			.commit();
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.suitepad.menuitem");
			this.registerReceiver(mBroadcastReceiver, filter);
		}
	}

	/**
	 * Unregister receiver to prevent leak
	 */
	@Override
	protected void onStop() 
	{
		super.onStop();
		try
		{
			this.unregisterReceiver(mBroadcastReceiver);
		}
		catch(Exception e)
		{
			System.out.println("No receiver registered");
		}
		while(mFragmentManager.getBackStackEntryCount()>0)
		{
			mFragmentManager.popBackStack();
		}
	}

	public void switchFragment()
	{
		mFragmentManager.beginTransaction()
		.replace(R.id.container, new MenuFragment(menuItems,mType,priceItems)).addToBackStack(null)
		.commit();

	}
	@Override
	public void onBackPressed() 
	{
		if ( mFragmentManager.getBackStackEntryCount() > 0) 
		{
			getFragmentManager().popBackStack();
			return;
		}
		super.onBackPressed();
	}

}



