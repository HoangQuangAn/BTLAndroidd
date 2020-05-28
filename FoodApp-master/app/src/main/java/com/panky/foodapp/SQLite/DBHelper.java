package com.panky.foodapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.panky.foodapp.Model.MonAn;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBName="monan.db";
    private static final int VERSION=1;
    private static final String TABLE_NAME="MonAn";
    private static final String ID ="id";
    private static final String NAME="name";
    private static final String IMAGE="image";
    private SQLiteDatabase DBAn;

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, VERSION);
    }

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getIMAGE() {
        return IMAGE;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String a="CREATE TABLE "+TABLE_NAME+"("+ID+" TEXT PRIMARY KEY,"+NAME+" TEXT NOT NULL,"+IMAGE+" TEXT NOT NULL"+")";
        db.execSQL(a);
    }

    // mở CSDL
    public void OpenDB(){
        DBAn= getWritableDatabase();
    }

    public ArrayList<MonAn> GetMonAn(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<MonAn> MonAnList = new ArrayList<MonAn>();
        String query ="SELECT * FROM "+ TABLE_NAME  ;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            MonAn monAn = new MonAn(cursor.getString(cursor.getColumnIndex(ID)),cursor.getString(cursor.getColumnIndex(NAME)),cursor.getString(cursor.getColumnIndex(IMAGE)));
            MonAnList.add(monAn);
        }
        return  MonAnList;
    }

    public void CloseDB(){
        if (DBAn!=null && DBAn.isOpen()){
            DBAn.close();
        }
    }

    public long Delete(String id){
        String where=ID+" = "+id;
        return DBAn.delete(TABLE_NAME,where, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //thêm mới 1 dòng
    public long Insert(MonAn ma){
        DBAn=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ID, ma.getID());
        values.put(NAME,ma.getName());
        values.put(IMAGE,ma.getImage());
        return DBAn.insert(TABLE_NAME,null, values);
    }

    public Cursor getAllCursor(){
        String query="SELECT * FROM "+ TABLE_NAME ;
        return DBAn.rawQuery(query,null);
    }

    // xóa 1 dòng dữ liệu
    public long Delete(int id){
        String where=ID+"="+id;
        return DBAn.delete(TABLE_NAME,where, null);
    }
}
