package com.panky.foodapp.View.Home;

import android.support.annotation.NonNull;

import com.panky.foodapp.Model.Categories;
import com.panky.foodapp.Model.Meals;
import com.panky.foodapp.Utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// hiển thị dữ liêu lên Home Fragment
public class HomePresenter {

    private HomeView view;

    //tạo 1  contructor
    public HomePresenter(HomeView view) {
        this.view = view;
    }

    public void getMeals() {

        //phần loading trước khi request đến server
        view.onShowLoading();
        Call<Meals> mealsCall = Utils.getApi().getMeal();
        //Meals= List<Meal>

        // chờ CallBack
        mealsCall.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(@NonNull Call<Meals> call, @NonNull Response<Meals> response) {


                view.onHideLoading();

                if (response.isSuccessful() && response.body() != null) {

                    view.setMeal(response.body().getMeals());
                }
                else {
                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Meals> call, @NonNull Throwable t) {
                view.onHideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });
    }


   public void getCategories() {

        view.onShowLoading();
        Call<Categories> categoriesCall = Utils.getApi().getCategories();
        // chờ dữ liệu vêf
        categoriesCall.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {
                view.onHideLoading();
                if (response.isSuccessful() && response.body() != null) {

                    view.setCategory(response.body().getCategories());
                }
                else {

                    view.onErrorLoading(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {

                view.onErrorLoading(t.getLocalizedMessage());
                view.onHideLoading();
            }
        });
    }
}
