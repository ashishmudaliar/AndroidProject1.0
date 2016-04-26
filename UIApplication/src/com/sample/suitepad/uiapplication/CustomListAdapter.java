package com.sample.suitepad.uiapplication;

import java.util.List;

import com.example.uiapplication.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Ashish
 * Custom Adapter created to perform operations on TextView inside ListView
 */
class CustomListAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private int id;
	private List <String>items ;
	
	public CustomListAdapter(Context context, int textViewResourceId , List<String> list ) 
	{
		super(context, textViewResourceId, list);           
		mContext = context;
		id = textViewResourceId;
		items = list ;
		
	}

	@Override
	public View getView(int position, View v, ViewGroup parent)
	{
		View mView = v ;
		if(mView == null){
			LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView = vi.inflate(id, null);
		}

		TextView text = (TextView) mView.findViewById(R.id.suitepad_text_view);

		if(items.get(position) != null )
		{
			text.setText(items.get(position));
			if(items.get(position).equals("Appetizers"))
				text.setBackgroundResource(R.drawable.appetizers);
			else if(items.get(position).equals("Main Course"))
				text.setBackgroundResource(R.drawable.maincourse);
			else if(items.get(position).equals("Drinks"))
				text.setBackgroundResource(R.drawable.drinks);
			
		}

		return mView;
	}

}