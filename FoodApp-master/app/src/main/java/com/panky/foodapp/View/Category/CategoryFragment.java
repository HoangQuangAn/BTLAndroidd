package com.panky.foodapp.View.Category;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.panky.foodapp.Adapter.RecyclerViewMealByCategory;
import com.panky.foodapp.Constant.constantValues;
import com.panky.foodapp.Model.Meals;
import com.panky.foodapp.Model.MonAn;
import com.panky.foodapp.R;
import com.panky.foodapp.SQLite.DBHelper;
import com.panky.foodapp.Utils.Utils;
import com.panky.foodapp.loginPageActivity.FavouriteActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryView, constantValues, View.OnClickListener {

    private View view;
    private ImageView imgVCategory, imgVBGCategory;
    private TextView tvCategory;
    private RecyclerView recyclerViewCategory;
    private ProgressBar pbLoadingData;
    private CategoryPresenter categoryPresenter;
    private CardView cardCategory;


    private AlertDialog.Builder descDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);

        initViews();

        cardCategory.setOnClickListener(this);

        return view;
    }

    private void initViews() {
        imgVCategory = view.findViewById(R.id.imageCategory);
        imgVBGCategory = view.findViewById(R.id.imageCategoryBg);
        tvCategory = view.findViewById(R.id.textCategory);
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);
        pbLoadingData = view.findViewById(R.id.progressBar);
        cardCategory = view.findViewById(R.id.cardCategory);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            tvCategory.setText(getArguments().getString(EXTRA_CATEGORY_DESC));
            Picasso.get().
                    load(getArguments().
                            getString(EXTRA_CATEGORY_THUMB))
                    .placeholder(R.drawable.ic_circle)
                    .into(imgVCategory);

            Picasso.get().
                    load(getArguments().
                            getString(EXTRA_CATEGORY_THUMB))
                    .placeholder(R.drawable.ic_circle)
                    .into(imgVBGCategory);

            descDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getArguments().getString(EXTRA_CATEGORY_NAME))
                    .setMessage(getArguments().getString(EXTRA_CATEGORY_DESC));

            categoryPresenter = new CategoryPresenter(this);
            categoryPresenter.getMealByCategory(getArguments().getString(EXTRA_CATEGORY_NAME));
        }
    }


    @Override
    public void setMeals(final List<Meals.Meal> meals) {

        RecyclerViewMealByCategory mealByCategory = new RecyclerViewMealByCategory(getActivity(), meals);
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewCategory.setClipToPadding(false);
        recyclerViewCategory.setAdapter(mealByCategory);
        mealByCategory.notifyDataSetChanged();

        mealByCategory.setOnItemClickListener(new RecyclerViewMealByCategory.ClickListener() {
            @Override
            public void onClick(View view, int position) {
               // MonAn monAn= new MonAn(meals.get(position).getIdMeal(),meals.get(position).getStrMeal(),meals.get(position).getStrMealThumb(),meals.get(position).getStrSource());
                Toast.makeText(getActivity(), "Đã Thêm:: "+meals.get(position).getStrMeal()+" Vào Danh Sách Yêu Thích", Toast.LENGTH_SHORT).show();
              // Toast.makeText(getActivity(), meals.get(position).str(), Toast.LENGTH_SHORT).show();

                Intent intentCategory = new Intent(getActivity(), FavouriteActivity.class);
                intentCategory.putExtra("id", meals.get(position).getIdMeal());
                intentCategory.putExtra("name", meals.get(position).getStrMeal());
                intentCategory.putExtra("image", meals.get(position).getStrMealThumb());
                intentCategory.putExtra("desc", meals.get(position).getStrYoutube());
                //intentCategory.putExtra(EXTRA_POSITION, position);

                Log.d("MS","id"+ meals.get(position).getIdMeal() );
                startActivity(intentCategory);

            }
        });

    }


    @Override
    public void showLoading() {
        pbLoadingData.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoadingData.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @Override
    public void onClick(View v) {
                descDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        descDialog.show();
    }


}