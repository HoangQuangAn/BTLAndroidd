package com.panky.foodapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.panky.foodapp.Model.MonAn;
import com.panky.foodapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomListView extends BaseAdapter {
    private Context context;
    private ArrayList<MonAn> dsMonAn;

    public CustomListView(Context context, ArrayList<MonAn> dsMonAn) {
        this.context = context;
        this.dsMonAn = dsMonAn;
    }

    @Override
    public int getCount() {
        return dsMonAn.size();
    }

    @Override
    public Object getItem(int position) {
        return dsMonAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.item_recycler_meal2,null);
        }
        MonAn monAn=(MonAn) dsMonAn.get(position);
        if (monAn!=null){
            ImageView imageView=(ImageView)view.findViewById(R.id.mealThumb);
            Picasso.get().
                    load(monAn.getImage())
                    .placeholder(R.drawable.ic_circle)
                    .into(imageView);
            TextView textView= (TextView)view.findViewById(R.id.mealName);
            textView.setText(monAn.getName());
        }
        return view;
    }

}
