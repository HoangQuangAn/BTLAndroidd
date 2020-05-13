package com.panky.foodapp.FoodApp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.panky.foodapp.Adapter.RecyclerViewHomeAdapter;
import com.panky.foodapp.Adapter.ViewPagerHeaderAdapter;
import com.panky.foodapp.Model.Categories;
import com.panky.foodapp.Model.Meals;
import com.panky.foodapp.R;
import com.panky.foodapp.Utils.Utils;
import com.panky.foodapp.View.Category.CategoryActivity;
import com.panky.foodapp.View.Home.HomePresenter;
import com.panky.foodapp.View.Home.HomeView;
import com.panky.foodapp.Constant.constantValues;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements HomeView, constantValues {

    private View view;
    private ViewPager viewPagerHeader;
    private RecyclerView categoryItemRV;
    private WormDotsIndicator indicatorTabLt;
    List<Meals.Meal> getMeals;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews();
        HomePresenter presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        getMeals = new ArrayList<>();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragment.SliderTimer(), 3000, 4000);


        return view;
    }

    private void initViews() {
        viewPagerHeader = view.findViewById(R.id.viewPagerHeader);
        categoryItemRV = view.findViewById(R.id.categoryItemRV);
        indicatorTabLt = view.findViewById(R.id.indicatorTabLt);
    }

    @Override
    public void onShowLoading() {

        view.findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        view.findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        view.findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        view.findViewById(R.id.shimmerCategory).setVisibility(View.GONE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {

        getMeals = meal;
        for (Meals.Meal mealResult : meal){
            Log.e("meals :: ", ""+mealResult.getStrMeal());
        }

        ViewPagerHeaderAdapter pagerHeaderAdapter = new ViewPagerHeaderAdapter(meal, getActivity());
        viewPagerHeader.setAdapter(pagerHeaderAdapter);
        viewPagerHeader.setPadding(20,0,150, 0);
        pagerHeaderAdapter.notifyDataSetChanged();

        pagerHeaderAdapter.setOnItemClickListener(new ViewPagerHeaderAdapter.ClickListener() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
        indicatorTabLt.setViewPager(viewPagerHeader);
    }

    @Override
    public void setCategory(final List<Categories.Category> category) {

        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, getActivity());
        categoryItemRV.setAdapter(homeAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3,
                GridLayoutManager.VERTICAL, false);
        categoryItemRV.setLayoutManager(gridLayoutManager);
        categoryItemRV.setClipToPadding(false);
        categoryItemRV.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener(new RecyclerViewHomeAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();

                Intent intentCategory = new Intent(getActivity(), CategoryActivity.class);
                intentCategory.putExtra(EXTRA_CATEGORY, (Serializable) category);
                intentCategory.putExtra(EXTRA_POSITION, position);
                startActivity(intentCategory);
            }
        });
    }

    @Override
    public void onErrorLoading(String message) {

        Utils.showDialogMessage(getActivity(), "title", message);
    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if(getActivity() != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPagerHeader.getCurrentItem() <  getMeals.size()- 1) {
                            Log.e("ViewPager :: " + viewPagerHeader.getCurrentItem(), ", fieldlist :: " + (getMeals.size() - 1));

                            viewPagerHeader.setCurrentItem(viewPagerHeader.getCurrentItem() + 1);
                            Log.e("viewPag SetCurrItem", " :: " + (viewPagerHeader.getCurrentItem() + 1));
                        } else {
                            viewPagerHeader.setCurrentItem(0);
                        }
                    }
                });
            }
            }
    }
}
