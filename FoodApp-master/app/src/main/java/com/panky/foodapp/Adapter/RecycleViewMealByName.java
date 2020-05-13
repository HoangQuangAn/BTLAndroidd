package com.panky.foodapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.panky.foodapp.Model.Meals;
import com.panky.foodapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewMealByName extends RecyclerView.Adapter<RecycleViewMealByName.RecyclerViewHolder>{

    private List<Meals.Meal> meals;
    private Context context;
    private static RecycleViewMealByName.ClickListener clickListener;

    public RecycleViewMealByName(Context context, List<Meals.Meal>meals){
        this.meals=meals;
        this.context=context;
    }

    @NonNull
    @Override
    public RecycleViewMealByName.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(context).inflate(R.layout.item_recycler_meal, viewGroup,false);
        return new RecycleViewMealByName.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewMealByName.RecyclerViewHolder viewHolder, int i){

        String strMealThumb=meals.get(i).getStrMealThumb();
        Picasso.get().load(strMealThumb).placeholder(R.drawable.shadow_bottom_to_top).into(viewHolder.mealThumb);

        String strMealName=meals.get(i).getStrMeal();
        viewHolder.mealName.setText(strMealName);
    }


    public void setOnItemClickListener(RecycleViewMealByName.ClickListener clickListener) {
        RecycleViewMealByName.clickListener = clickListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);
    }

    @Override
    public int getItemCount(){
        Log.e("meals Size :: ", ""+meals.size());
        return meals.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mealThumb;
        TextView mealName;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            mealName = itemView.findViewById(R.id.mealName);
            mealThumb = itemView.findViewById(R.id.mealThumb);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
