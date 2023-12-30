package com.example.exaqlite04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDB {
    public SQLiteDatabase db=null;
    private final static String Database_name="db1.db";
    private final static String Table_name="table01";
    private final static String _Id="id";
    private final static String Name="name";
    private final static String Price="price";

    private final static String Create_Table= "CREATE TABLE " + Table_name + "(" + _Id
            + "integer primary key," + Name + "text,"+ Price + "integer)";

    private Context MCtx=null;
    public MyDB(Context ctx){
        this.MCtx=ctx;}
    public void open() throws SQLException{
        db=MCtx.openOrCreateDatabase(Database_name,0,null);
        try {
            db.execSQL(Create_Table);
        }
        catch (Exception e){

        }
    }
    public void close(){
        db.close();
    }
    public Cursor getAll(){
        return db.query(Table_name,new String[] {_Id,Name,Price},
                null,null,null,null,null,null);
    }
    public Cursor get(long RowId) throws SQLException{
        Cursor MCursor=db.query(Table_name,new String[] {_Id,Name,Price},
                _Id +"="+RowId,null,null,null,null,null);
        if (MCursor!=null){
            MCursor.moveToFirst();
        }
        return MCursor;
    }
    public long append(String n,int p){
        ContentValues args=new ContentValues();
        args.put(Name,n);
        args.put(Price,p);
        return db.insert(Table_name,null,args);
    }
    public boolean delete(long RowId){
        return db.delete(Table_name, _Id + "=" + RowId, null) > 0;
    }
    public boolean update(long rowId,String n,int p){
        ContentValues args=new ContentValues();
        args.put(Name,n);
        args.put(Price,p);
        return db.update(Table_name, args,_Id + "=" + rowId, null) > 0;
    }
}
