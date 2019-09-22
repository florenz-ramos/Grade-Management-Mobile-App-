package com.calyx.dev.app;


import android.content.*;
import android.database.sqlite.*;
import android.database.*;

public class SQLITEDBUtil extends SQLiteOpenHelper
{
	public SQLITEDBUtil(Context c){
		super(c,SQLITEParams.DBNAME,null,1);
	}

	public int DeleteQuery(String tblName,String col,String val){
		SQLiteDatabase db=this.getWritableDatabase();
		int i=db.delete(tblName,col+"=?",new String[]{val});
		return i;
	}
	
	
	
	public Cursor Select(String query)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res=db.rawQuery(query,null);
		return res;
	}
	public Cursor SelectQuery(String query,String col,String val){
		
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor res=db.rawQuery(query,new String[]{val});
		return res;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		for(int i=0;i<SQLITEParams.CREATE.length;i++){
			p1.execSQL(SQLITEParams.CREATE[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		for(String s:SQLITEParams.TABLES){
			p1.execSQL("DROP TABLE IF EXISTS "+s);
		}
		onCreate(p1);
	}
	public Boolean insert(String tablename,String[] columns,String[] values){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		for(int i=0;i<columns.length;i++){
			cv.put(columns[i],values[i]);
		}
		long res=db.insert(tablename,null,cv);
		db.close();
		return (res==-1?false:true);
	}
	public boolean UpdateQuery(String tbl,String where,String[] col, String[] val){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		for(int i=0;i<col.length;i++){
			cv.put(col[i],val[i]);
		}
		int res=db.update(tbl,cv,where+"=?",new String[]{val[0]});
		return (res>0?true:false);
	}


}
