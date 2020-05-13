package com.panky.foodapp.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.panky.foodapp.Constant.constantValues;
import com.panky.foodapp.Model.Categories;
import com.panky.foodapp.View.Category.CategoryFragment;

import java.util.List;

public class ViewPagerCategoryAdapter extends FragmentPagerAdapter implements constantValues {

    private List<Categories.Category> categories;

    public ViewPagerCategoryAdapter(FragmentManager fm, List<Categories.Category> categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int i) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();

        args.putString(EXTRA_CATEGORY_NAME, categories.get(i).getStrCategory());
        args.putString(EXTRA_CATEGORY_THUMB, categories.get(i).getStrCategoryThumb());
        args.putString(EXTRA_CATEGORY_DESC, categories.get(i).getStrCategoryDescription());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getStrCategory();
    }
}
