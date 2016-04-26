package com.sample.suitepad.uiapplication;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.uiapplication.R;

public class ItemFragment extends Fragment 
{

	ArrayList<String> itemList = new ArrayList<String>();
	MainActivity mActivity;
	public ItemFragment(ArrayList<String> list) 
	{
		itemList = list;
	}

	@Override
	public void onAttach(Activity activity) 
	{
		mActivity = (MainActivity) activity;
		super.onAttach(activity);
	}
	void getMenu(String type)
	{
		mActivity.mType = type;
		Intent intent = new Intent("com.suitepad.getlist");
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		intent.putExtras(bundle);
		getActivity().sendBroadcast(intent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		TextView noServiceTextView = (TextView) rootView.findViewById(R.id.noServiceText);
		TextView textView = (TextView) rootView.findViewById(R.id.itemText);
		noServiceTextView.setText("Support Service is not installed. Please install service to get menu");
		textView.setText("Welcome to Restaurant. Please Select Category");
		ListView listView = (ListView) rootView.findViewById(R.id.type_list);
		listView.setAdapter(new CustomListAdapter(getActivity(), R.layout.textview_list_suitepad, itemList));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				getMenu(itemList.get(position));

			}
		});

		if(mActivity.flag == 0)
		{
			textView.setVisibility(View.INVISIBLE);
			listView.setVisibility(View.INVISIBLE);
			noServiceTextView.setVisibility(View.VISIBLE);
		}
		else
		{
			textView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
			noServiceTextView.setVisibility(View.INVISIBLE);
		}
		return rootView;
	}
}

