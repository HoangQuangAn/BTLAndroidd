package com.panky.foodapp.Utils;

import android.app.AlertDialog;
import android.content.Context;

import com.panky.foodapp.RESTapi.FoodApi;
import com.panky.foodapp.RESTapi.FoodClient;

public class Utils {


    public static FoodApi getApi() {
        return FoodClient.getFoodClient().create(FoodApi.class);
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }
}
