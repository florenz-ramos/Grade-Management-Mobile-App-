package com.calyx.dev.app;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import java.util.*;
import android.database.*;
import android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity 
{
	
	SQLITEDBUtil dbutil;
	FloatingActionButton fabClass;
	ListView lvl;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
			  dbutil =new SQLITEDBUtil(this);			
			  init();
    }
	public static String className;
	
	private void init(){
		try{
				lvl=(ListView)findViewById(R.id.listOfClasses);
			final ArrayAdapter ad=new ClassAdapter(getApplicationContext(),PopulateClasses());
			lvl.setAdapter(ad);
			
			fabClass=(FloatingActionButton)findViewById(R.id.btnAddClass);
			fabClass.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View p1){
						AddClass();
					}
				});
		
			
			lvl.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
					{
						className=ClassAdapter.classes.get(p3);
						Intent i=new Intent(getApplicationContext(),StudentActivity.class);
						startActivity(i);
			
						
						//Toast.makeText(getApplicationContext(),className,Toast.LENGTH_SHORT).show();

					}
			});
			lvl.setOnItemLongClickListener(new OnItemLongClickListener(){

					@Override
					public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
					{
						Delete(ad,p3);
						
						return true;
					}

				
				
			});
			
			
		}
		catch(Exception ex){
			Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
		}
	}
	private void Delete(final ArrayAdapter ad,int p3){
		
		try{
			AlertDialog.Builder adb=new AlertDialog.Builder(this);
			adb.setTitle("Message");
			adb.setMessage("Delete Note Successfully");
			//adb.setNegativeButton("Cancel",null);
			final int pos=p3;
			adb.setPositiveButton("Ok", new AlertDialog.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2){
						dbutil.DeleteQuery("tblclass","CLASSID",ClassAdapter.classes.get(pos));
						dbutil.DeleteQuery("tblstudents","CLASSID",ClassAdapter.classes.get(pos));
						dbutil.DeleteQuery("tblgrades","CLASSID",ClassAdapter.classes.get(pos));
						ad.remove(ad.getItem(pos));
						ad.notifyDataSetChanged();
					}		
				});
			adb.show();
		}
		catch(Exception ex){
			Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
		}

		
		
		
	}
	
	
	
	private List<String> PopulateClasses(){
		
		List<String> lst=new ArrayList<String>();
		String query="SELECT CLASSID from tblclass ORDER BY ID DESC";
		Cursor c=dbutil.Select(query);
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
				lst.add(c.getString(0));
			}
		}
		else{
		 Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();
		 }
		return lst;
	}
  private void AddClass(){
		try{
			AlertDialog.Builder ab= new AlertDialog.Builder(this);
			ab.setTitle("ADD NEW CLASS");
			ab.setMessage("CLASS NAME:");
			final EditText text=new EditText(this);
			ab.setView(text);
			ab.setPositiveButton("Save", new AlertDialog.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						dbutil.insert("tblclass",
						new String[]{"CLASSID"},
						new String[]{text.getText().toString()}
						);
						final ArrayAdapter ad=new ClassAdapter(getApplicationContext(),PopulateClasses());
						lvl.setAdapter(ad);
						

						Toast.makeText(getApplicationContext(),"Save Successfully",Toast.LENGTH_LONG).show();
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
	
}
