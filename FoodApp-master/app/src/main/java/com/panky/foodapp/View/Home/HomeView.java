package com.panky.foodapp.View.Home;

import com.panky.foodapp.Model.Categories;
import com.panky.foodapp.Model.Meals;

import java.util.List;

public interface HomeView {

    void onShowLoading(); // hàm hiển thị khi đang Load
    void onHideLoading(); // hàm ẩn Load
    void setMeal(List<Meals.Meal> meal);
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);
}
