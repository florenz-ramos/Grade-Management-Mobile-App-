package com.calyx.dev.app;
import android.widget.*;
import android.content.*;
import java.util.*;
import android.view.*;

public class StudentAdapter extends ArrayAdapter
{
	public static List<String> fullname,studentID;
	public StudentAdapter(Context c,List<String> fn,List<String> sid){
		super(c,R.layout.custom_student_layout,sid);
		this.fullname=fn;
		this.studentID=sid;
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
		View row=inflater.inflate(R.layout.custom_student_layout,parent,false);

		TextView stdID=(TextView)row.findViewById(R.id.lblStudentID);
		TextView fn=(TextView)row.findViewById(R.id.lblFullname);
		stdID.setText(studentID.get(position));
		fn.setText(fullname.get(position));
		return row;
	}
	
	
	
	
	
}
