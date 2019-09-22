package com.calyx.dev.app;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.view.*;

public class ClassAdapter extends ArrayAdapter
{
	public static List<String> classes;
	public ClassAdapter(Context c,List<String> cl)
	{
		super(c,R.layout.custom_class_layout,cl);
		this.classes=cl;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO: Implement this method
		return super.getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.custom_class_layout,parent,false);
		TextView cl=(TextView)row.findViewById(R.id.lblClass);
		cl.setText(classes.get(position));
		return row;
		
	}
	
	
	
	
}
