package com.panky.foodapp.RESTapi;

import com.panky.foodapp.Model.Categories;
import com.panky.foodapp.Model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {

    /*This is all are request of GET */
    @GET("search.php?f=b")
    Call<Meals> getMeal();

    @GET("categories.php")
    Call<Categories> getCategories();

    @GET("filter.php")
    Call<Meals> getMealsByCategory(@Query("c") String category);
}
