package com.calyx.dev.app;
import android.content.*;
import android.database.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;
import android.view.View.OnClickListener;
public class StudentActivity extends AppCompatActivity
{
	SQLITEDBUtil dbutil;
	FloatingActionButton fabClass;
	ListView lvl;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student);
		setTitle(MainActivity.className);
		dbutil=new SQLITEDBUtil(this);
		Init();
	}
	public static String stdID;
	public static String fullname;
	private void Init(){
		try{
			fabClass=(FloatingActionButton)findViewById(R.id.btnAddStudent);
			fabClass.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						AddStudentID();
						
					}		
			});
			lvl=(ListView)findViewById(R.id.listOfStudents);
			final ArrayAdapter ad=new StudentAdapter(getApplicationContext(),PopulateName(),PopulateID());
			lvl.setAdapter(ad);
			

			lvl.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
					{
					  stdID=StudentAdapter.studentID.get(p3);
						fullname=StudentAdapter.fullname.get(p3);
						Intent i=new Intent(getApplicationContext(),GradeActivity.class);
						startActivity(i);				
					}

				
				
			});
			
			
			
			
			
		}
		catch(Exception ex){
			Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
		}
		
		
	}
	private List<String> PopulateID(){

		List<String> lst=new ArrayList<String>();
		String query="SELECT ID from tblstudents WHERE CLASSID='"+MainActivity.className+"' ORDER BY FULLNAME ASC ";
		Cursor c=dbutil.Select(query);
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
				lst.add(c.getString(0));
			}
		}
	/*	else{
			Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();
		}*/
		return lst;
	}
	private List<String> PopulateName(){

		List<String> lst=new ArrayList<String>();
		String query="SELECT FULLNAME from tblstudents WHERE CLASSID='"+MainActivity.className+"' ORDER BY FULLNAME ASC ";
		Cursor c=dbutil.Select(query);
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
				lst.add(c.getString(0));
			}
		}
		/*else{
			Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();
		}*/
		return lst;
	}
	
	
	
	
	private void AddStudentID(){
		try{
		AlertDialog.Builder ab= new AlertDialog.Builder(this);
		ab.setTitle("Add Student ID");
		final EditText studentID=new EditText(this);
	  ab.setView(studentID);
		
		ab.setPositiveButton("Next", new AlertDialog.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						AddFullname(studentID.getText().toString());
						
					}	
		});
			ab.setNegativeButton("Cancel", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
					}

			
			
		});
		
		
		ab.show();
		
		
		}
		catch(Exception ex){
			Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
		}
		
	}
	private void AddFullname(final String studentID){
		try{
			AlertDialog.Builder adb= new AlertDialog.Builder(this);
			adb.setTitle("Add Student Name");
			final EditText fullname=new EditText(this);
			adb.setView(fullname);
			adb.setPositiveButton("Save", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						
						dbutil.insert("tblstudents",
						new String[]{"ID","FULLNAME","CLASSID"},
						new String[]{
							studentID,
						  fullname.getText().toString(),
							MainActivity.className
						 }
						);
					
					  Show("Save Successfully!");
						final ArrayAdapter ad=new StudentAdapter(getApplicationContext(),PopulateName(),PopulateID());
						lvl.setAdapter(ad);
						
						
						
					}
			});
			adb.show();
		}
		catch(Exception ex){
		
			
		}
		
	}
	
	private void Show(String m){
		Toast.makeText(this,m,Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
			case R.id.passers:
				Restrctions();
				break;
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
 private void Restrctions(){
	 try{
		 AlertDialog.Builder ab=new AlertDialog.Builder(this);
		 ab.setTitle("Unaccessible...");
		 ab.setMessage("Please contact the developer in order "
		          +"to grant your request");
		 ab.setPositiveButton("OK", new AlertDialog.OnClickListener(){

				 @Override
				 public void onClick(DialogInterface p1, int p2)
				 {
					 // TODO: Implement this method
				 }

			 
			 
		 });
							
							
		 ab.show();
	 }
	 catch(Exception ex){
		 
	 }
	 
	 
	 
 }
	
	
	
	
	
	
	
	
	
}
