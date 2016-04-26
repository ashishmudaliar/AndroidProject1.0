package com.sample.suitepad.uiapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.uiapplication.R;

public class MenuFragment extends Fragment 
{

	ArrayList<String> itemList = new ArrayList<String>();
	ArrayList<String> priceList = new ArrayList<String>();
	String mType;
	MainActivity mActivity;
	public MenuFragment(ArrayList<String> list,String type,ArrayList<String> price) 
	{
		itemList = list;
		priceList = price;
		mType = type;
	}

	@Override
	public void onAttach(Activity activity) 
	{
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

		ListView listView = (ListView) rootView.findViewById(R.id.name_list);
		ListView listViewPrice = (ListView) rootView.findViewById(R.id.price_list);
		View headerItemView = inflater.inflate(R.layout.header, listView,false);
		View headerPriceView = inflater.inflate(R.layout.header, listViewPrice,false);
		TextView headerItem = (TextView) headerItemView.findViewById(R.id.header_view);
		TextView headerPrice = (TextView) headerPriceView.findViewById(R.id.header_view);
		headerItem.setText(mType);
		headerPrice.setText("Price in Euros");
		listView.addHeaderView(headerItemView);
		listViewPrice.addHeaderView(headerPriceView);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemList));
		listViewPrice.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, priceList));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{

			}
		});


		return rootView;
	}
}
