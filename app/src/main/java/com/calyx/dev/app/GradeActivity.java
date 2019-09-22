package com.calyx.dev.app;
import android.support.v7.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.graphics.*;
import android.content.*;
import android.database.*;
public class GradeActivity extends AppCompatActivity
{
  TextView lblstudentID,lblFullname,lblGrade,lblGradeEquiv;
	EditText txtPrelim,txtMidterm,txtFinals;
	Button btnCompute,btnSaveGrade;
	SQLITEDBUtil util;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grades);
		util=new SQLITEDBUtil(this);
		init();
		if(hasGrade()){
			btnSaveGrade.setText("UPDATE");
		}
		
	}
	private void init(){
		lblstudentID=(TextView)findViewById(R.id.gstdid);
		lblFullname=(TextView)findViewById(R.id.gstdname);
		lblGrade=(TextView)findViewById(R.id.lblGrade);
		lblGradeEquiv=(TextView)findViewById(R.id.lblGradeEquivalent);


		lblstudentID.setText(StudentActivity.stdID);
		lblFullname.setText(StudentActivity.fullname);
		txtPrelim=(EditText)findViewById(R.id.txtPrelim);
		txtMidterm=(EditText)findViewById(R.id.txtMidterm);
		txtFinals=(EditText)findViewById(R.id.txtFinals);
		btnCompute=(Button)findViewById(R.id.btnCompute);
		btnSaveGrade=(Button)findViewById(R.id.btnSaveGrade);
		
		btnCompute.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					if(txtPrelim.getText().toString().equals("")){
						txtPrelim.setText("0");
					}
					if(txtMidterm.getText().toString().equals("")){
						txtMidterm.setText("0");
					}
					if(txtFinals.getText().toString().equals("")){
						txtFinals.setText("0");
					}

					double prelim=Double.parseDouble(txtPrelim.getText().toString());
					double midterm=Double.parseDouble(txtMidterm.getText().toString());
					double finals=Double.parseDouble(txtFinals.getText().toString());

					double average=(prelim+midterm+finals)/3;
					String av=String.valueOf(average);
					lblGrade.setText(av);		
					SetEquivalence(average);
				}
			});	

		btnSaveGrade.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Save();
				}


			});
	}
	
	
	
	private void Save(){
		try{
			AlertDialog.Builder ab=new AlertDialog.Builder(this);
			if(btnSaveGrade.getText().equals("UPDATE")){
			ab.setTitle("Updating Grades...");
			ab.setMessage("Do you want to update grades?");
			}
			else{
				ab.setTitle("Saving Grades...");
				ab.setMessage("Do you want to save grades?");
			}
			
			ab.setPositiveButton("Yes", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						String[] columns={
							"STUDENTID","PRELIM","MIDTERM","FINALS","AVERAGE","CLASSID"};
						String[] values={
							lblstudentID.getText().toString(),
							txtPrelim.getText().toString(),
							txtMidterm.getText().toString(),
							txtFinals.getText().toString(),
							lblGrade.getText().toString(),
							MainActivity.className
							};
						if(btnSaveGrade.getText().equals("SAVE"))
						{
							util.insert("tblgrades",columns,values);
							Show("Saved!",Toast.LENGTH_SHORT);
							clearFields();
							finish();
						}
						if(btnSaveGrade.getText().equals("UPDATE")){
							util.UpdateQuery("tblgrades","STUDENTID",columns,values);
							Show("Updated!",Toast.LENGTH_SHORT);
							clearFields();
							finish();
						}
					}
				});
			ab.setNegativeButton("NO", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
					}

					
				});
				
				
				
				ab.show();
			
			
		}
		catch(Exception ex){
			Show(ex.getMessage(),Toast.LENGTH_LONG);
		}
		
	}
	
	private void Show(String s,int ls){
		Toast.makeText(this,s,ls).show();
	}
	
	
	
	private void SetEquivalence(double ave){
		if(ave>=98.0&& ave<=100){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			lblGradeEquiv.setText("1.00");
		}
		else if(ave>=95.0 && ave<=97.9){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			lblGradeEquiv.setText("1.25");
		}
		else if(ave>=91.0 && ave<=94.9){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			lblGradeEquiv.setText("1.50");
		}
		else if(ave>=88.0 && ave<=90.9){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			lblGradeEquiv.setText("1.75");
		}
		else if(ave>=84.0 && ave<=87.9){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			lblGradeEquiv.setText("2.00");
			
		}
		else if(ave>=81.0 && ave<=83.9){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			
			lblGradeEquiv.setText("2.25");
		}
		else if(ave>=80.0 && ave<=76.9){
			lblGrade.setTextColor(Color.GREEN);
			lblGradeEquiv.setTextColor(Color.GREEN);
			lblGradeEquiv.setText("2.75");
		}
		else if(ave>=75.0&&ave<=75.9){
			lblGrade.setTextColor(Color.YELLOW);
			lblGradeEquiv.setTextColor(Color.YELLOW);
			lblGradeEquiv.setText("3.00");
		}
		else if(ave<=74.9){
			lblGrade.setTextColor(Color.RED);
			lblGradeEquiv.setTextColor(Color.RED);
			lblGradeEquiv.setText("5.00");
		}
	}
	public boolean hasGrade(){
		String query="SELECT * from tblgrades WHERE STUDENTID='"+StudentActivity.stdID+"'";
		Cursor c=util.Select(query);
		if(c!=null && c.getCount()>0){
			while(c.moveToNext()){
			
				txtPrelim.setText(c.getString(2));
				txtMidterm.setText(c.getString(3));
				txtFinals.setText(c.getString(4));
				lblGrade.setText(c.getString(5));
				double d=Double.parseDouble(c.getString(5));
				SetEquivalence(d);
				
			}
			return true;
		}
		else{
			return false;
	
		}
		
		
		
	}
	private void clearFields(){
		txtPrelim.setText("");
		txtMidterm.setText("");
		txtFinals.setText("");
		lblstudentID.setText("");
		lblFullname.setText("");
		lblGrade.setText("");
		lblGradeEquiv.setText("");
	}
	
	
	
	
}
