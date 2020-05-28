package com.panky.foodapp.loginPageActivity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.panky.foodapp.Adapter.CustomListView;
import com.panky.foodapp.Model.MonAn;
import com.panky.foodapp.R;
import com.panky.foodapp.SQLite.DBHelper;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {
    private DBHelper myDB;
    private Button btnShow;
    private CustomListView adapter;
    private ArrayList<MonAn> dsMonAn;
    private ListView lvDanhsach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        //ánh xạ
        btnShow= (Button)findViewById(R.id.buttonShow);
        myDB=new DBHelper(this);
        lvDanhsach=(ListView)findViewById(R.id.lvyeuThich);
        dsMonAn= new ArrayList<MonAn>();


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=  getIntent();
                String Id= intent.getStringExtra("id");

                String name=intent.getStringExtra("name");
                String image= intent.getStringExtra("image");

                //Toast.makeText(FavouriteActivity.this, "ID: "+Id+"Name:"+name+"Image: "+image, Toast.LENGTH_LONG).show();
                MonAn monAn= new MonAn(Id,name, image);

                long a= myDB.Insert(monAn);
                if(a==-1){
                    Toast.makeText(FavouriteActivity.this, "Thêm Thất Bại ", Toast.LENGTH_SHORT).show();
                }else
                {

                    Toast.makeText(FavouriteActivity.this, "Thêm Thành công ", Toast.LENGTH_SHORT).show();

                }

                dsMonAn=myDB.GetMonAn();
                adapter= new CustomListView(FavouriteActivity.this, dsMonAn);
                lvDanhsach.setAdapter(adapter);

            }
        });

        lvDanhsach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long resultDel=myDB.Delete(dsMonAn.get(position).getID());
                if(resultDel==0){
                    Toast.makeText(FavouriteActivity.this, "Delete Error", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(FavouriteActivity.this, "Delete Success", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        //Log.d("AB","ID: "+Id+"Name:"+name+"Image: "+image+"Descr: "+desc );

        //Toast.makeText(this, "Thêm Thành công ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDB.OpenDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDB.CloseDB();
    }
}
