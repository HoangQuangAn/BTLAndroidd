package com.panky.foodapp.View.Home;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.panky.foodapp.FoodApp.Fragments.AccountFragment;
import com.panky.foodapp.FoodApp.Fragments.CartFragment;
import com.panky.foodapp.FoodApp.Fragments.HomeFragment;
import com.panky.foodapp.FoodApp.Fragments.OffersFragment;
import com.panky.foodapp.FoodApp.Fragments.SearchFragment;
import com.panky.foodapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FrameLayout food_container;
    private BottomNavigationView bottomNavigationViewLeft, bottomNavigationViewRight;
    private FloatingActionButton fabCart;
    private Fragment foodMainFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();



        bottomNavigationViewLeft.setOnNavigationItemSelectedListener(this);
        bottomNavigationViewRight.setOnNavigationItemSelectedListener(this);

        onFragmentReplace(new HomeFragment(), true);

       // bottomNavigationViewRight.getMenu().findItem(R.id.food_offers).setChecked(false);
        bottomNavigationViewLeft.getMenu().findItem(R.id.food_home).setChecked(false);
        fabCart.setOnClickListener(this);
    }


    private void updateActionBarTitle(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragClassName.equals(HomeFragment.class.getName())) {
            fragmentManager.popBackStack(fragClassName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager = getSupportFragmentManager();
        foodMainFragment = getSupportFragmentManager().findFragmentById(R.id.food_container);
        Log.e("frag count :: ", ""+fragmentManager.getBackStackEntryCount()+" ;; "+ foodMainFragment);
        Log.e("frag count Res :: ", ""+fragmentManager.getBackStackEntryCount());
    }

    private void initViews() {
        food_container = findViewById(R.id.food_container);
        bottomNavigationViewLeft = findViewById(R.id.food_bottom_navigation_Left);
        bottomNavigationViewRight = findViewById(R.id.food_bottom_navigation_Right);
        fabCart = findViewById(R.id.fabCart);
    }

    // thay thế các fragment
    public void onFragmentReplace(Fragment fragment, boolean flag) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (flag) {
            if (!getSupportFragmentManager().popBackStackImmediate(getSupportFragmentManager().getClass().getName(), 0)) {
                fragmentTransaction.add(R.id.food_container, fragment, fragment.getClass().getName());
                fragmentTransaction.addToBackStack(getSupportFragmentManager().getClass().getName());
                fragmentTransaction.commit();
            }
        } else {
//            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(R.id.food_container, fragment, fragment.getClass().getName());
            fragmentTransaction.addToBackStack(getSupportFragmentManager().getClass().getName());
            fragmentTransaction.commit();
        }
    }

    private void toastMessage(String text) {
        Toast.makeText(this, "You Clicked " + text + " !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.food_home:
                onFragmentReplace(new HomeFragment(), false);
                break;

            //case R.id.food_search:
                //onFragmentReplace(new SearchFragment(), false);
                //break;

            /*case R.id.navigation_cart:
                onFragmentReplace(new CartFragment(), false);
                break;*/

           // case R.id.food_offers:
               // onFragmentReplace(new OffersFragment(), false);
               // break;

            case R.id.navigation_profile:
                onFragmentReplace(new AccountFragment(), false);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        Log.e("frag count :: ", ""+fragmentManager.getBackStackEntryCount()+" ;; "+ foodMainFragment);
        Log.e("frag count Res :: ", ""+fragmentManager.getBackStackEntryCount());

            if (fragmentManager.getBackStackEntryCount() > 1) {

                Log.e("frag count :: ", ""+fragmentManager.getBackStackEntryCount()+" ;; "+ foodMainFragment);
                Log.e("frag count Res :: ", ""+fragmentManager.getBackStackEntryCount());

                String fragName = foodMainFragment.getClass().getName().toString();
                if (foodMainFragment instanceof HomeFragment) {
                    Log.e("frag count :: ", ""+fragmentManager.getBackStackEntryCount()+" ;; "+ foodMainFragment);
                    Log.e("frag count Res :: ", ""+fragmentManager.getBackStackEntryCount()+" ;; "+fragName);

//                    ((AccountFragment)foodMainFragment).onBackPressed();
                    fragmentManager.popBackStack(fragName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else {
                    fragmentManager.popBackStack();
                }
            } else {
                // Otherwise, ask user if he wants to leave :)
                new AlertDialog.Builder(this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                // MainActivity.super.onBackPressed();

                                moveTaskToBack(true);
                                finish();
                            }
                        }).create().show();
            }
        }

    @Override
    public void onClick(View v) {
        onFragmentReplace(new CartFragment(), false);
    }
}
