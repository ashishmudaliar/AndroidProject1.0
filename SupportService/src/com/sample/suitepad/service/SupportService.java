package com.sample.suitepad.service;

import java.util.ArrayList;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class SupportService extends Service {


	ArrayList<Dish> menuItems = new ArrayList<Dish>();
	IBinder binder = new LocalBinder();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		menuItems.clear();
		/**
		 * Add items to list
		 */
		menuItems.add(new Dish("Spring Rolls", "Appetizers", "5.00"));
		menuItems.add(new Dish("Pizza","Main Course","7.00"));
		menuItems.add(new Dish("Fries", "Appetizers", "4.00"));
		menuItems.add(new Dish("Wine", "Drinks", "2.50"));
		menuItems.add(new Dish("Pasta","Main Course","7.00"));
		menuItems.add(new Dish("Beer", "Drinks", "1.50"));
		menuItems.add(new Dish("Nuggets", "Appetizers", "6.50"));
		menuItems.add(new Dish("Cola", "Drinks", "1.50"));
		menuItems.add(new Dish("Schnitzel","Main Course","8.00"));


		BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				Bundle bundle = intent.getExtras();
				String type = bundle.getString("type");
				ArrayList<String> itemList = new ArrayList<String>();
				ArrayList<String> priceList = new ArrayList<String>();
				for(int i = 0;i<menuItems.size();i++)
				{
					if(type.equals("All Items"))
					{
						itemList.add(menuItems.get(i).getName());
						priceList.add(menuItems.get(i).getPrice());
					}
					else if(menuItems.get(i).getType().equals(type))
					{
						itemList.add(menuItems.get(i).getName());
						priceList.add(menuItems.get(i).getPrice());
					}

				}
				Intent menuIntent = new Intent("com.suitepad.menuitem");
				menuIntent.putStringArrayListExtra("item list", itemList);
				menuIntent.putStringArrayListExtra("price", priceList);
				//Broadcast intent
				sendBroadcast(menuIntent);

			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.suitepad.getlist");
		this.registerReceiver(mBroadcastReceiver, filter);
		return 1;
	}


	public class LocalBinder extends Binder {
		public SupportService getServerInstance() {
			return SupportService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {

		Log.i("Service", "onBind called");
		return binder;

	}
}