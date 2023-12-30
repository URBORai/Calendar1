package com.example.mycalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class database {

    public SQLiteDatabase db=null;
    private final static String Database_name="db1.db";
    private final static String Table_name="table01";
    private final static String _Id="id";
    public final static String YEAR="year";
    public final static String MONTH="month";
    public final static String DAY="day";
    public final static String HOUR="hour";
    public final static String MIN="min";
    public final static String WHAT="what";
    public final static String YES="yes";


    private final static String Create_Table = "CREATE TABLE " + Table_name + "(" + _Id
            + " integer primary key," + HOUR + " integer," + MIN + " integer," + YEAR + " integer," + MONTH + " integer," + DAY + " integer," + YES + " integer," + WHAT + " text)";

    private Context MCtx=null;
    public database(Context ctx){
        this.MCtx=ctx;}
    public void open() throws SQLException {
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
        return db.query(Table_name,new String[] {_Id,HOUR,MIN,YEAR,MONTH,DAY,YES,WHAT},
                null,null,null,null,null,null);
    }
    public Cursor get(long RowId) throws SQLException{
        Cursor MCursor=db.query(Table_name,new String[] {_Id,HOUR,MIN,YEAR,MONTH,DAY,YES,WHAT},
                _Id +"="+RowId,null,null,null,null,null);
        if (MCursor!=null){
            MCursor.moveToFirst();
        }
        return MCursor;
    }
    public long append(int id,int h,int n,int p,int m,int d,int ye,String wh){
        ContentValues args=new ContentValues();
        args.put(_Id,id);
        args.put(HOUR,h);
        args.put(MIN,n);
        args.put(YEAR,p);
        args.put(MONTH,m);
        args.put(DAY,d);
        args.put(YES,ye);
        args.put(WHAT,wh);
        return db.insert(Table_name,null,args);
    }
    public boolean delete(long RowId){
        return db.delete(Table_name, _Id + "=" + RowId, null) > 0;
    }
    public boolean update(long rowId,int id, int h, int n, int p, int m, int d, int ye, String wh) {
        ContentValues args = new ContentValues();
        args.put(_Id,id);
        args.put(HOUR, h);
        args.put(MIN, n);
        args.put(YEAR, p);
        args.put(MONTH, m);
        args.put(DAY, d);
        args.put(YES, ye);
        args.put(WHAT, wh);
        return db.update(Table_name, args, _Id + "=" + rowId, null) > 0;
    }
    public Cursor getEventData(long rowId) throws SQLException {
        return db.query(Table_name, new String[] {YEAR, MONTH, DAY, HOUR, MIN, WHAT},
                _Id + "=" + rowId, null, null, null, null);
    }

}
