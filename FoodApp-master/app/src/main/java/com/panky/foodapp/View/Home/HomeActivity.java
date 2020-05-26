package com.panky.foodapp.View.Home;

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // xay dung csdl
    String DATABASE_NAME="monan.sqlite";
    String DB_PATH_SUFFIX="/databases/";
    SQLiteDatabase database=null;
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

    //hàm xử lí sao chép
        HamXuLiSaoChep();

        bottomNavigationViewLeft.setOnNavigationItemSelectedListener(this);
        bottomNavigationViewRight.setOnNavigationItemSelectedListener(this);

        onFragmentReplace(new HomeFragment(), true);

       // bottomNavigationViewRight.getMenu().findItem(R.id.food_offers).setChecked(false);
        bottomNavigationViewLeft.getMenu().findItem(R.id.food_home).setChecked(false);
        fabCart.setOnClickListener(this);
    }

    private void HamXuLiSaoChep() {
        File dbFile=getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Sao Chép CSDL VÀO hệ thống thành công", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "HamXuLiSaoChep: ");
            }
            catch (Exception s){
                Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try{
            InputStream myInput=getAssets().open(DATABASE_NAME);
            // trả về dường dẫn của DB
            String outFileName=LayDuongDan();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream myOutPut=new FileOutputStream(outFileName);
            byte[] buffer=new byte[1024];
            int length;
            while ((length=myInput.read(buffer))>0){
                myOutPut.write(buffer, 0 , length);
            }
            myOutPut.flush();
            myOutPut.close();
            myInput.close();

        }catch (Exception e){
            Log.e("LoiSaoChep", e.toString());

        }
    }

    private String LayDuongDan(){
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
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



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.food_home:
                onFragmentReplace(new HomeFragment(), false);
                break;
            case R.id.food_search:
                onFragmentReplace(new SearchFragment(), false);
                break;
            case R.id.food_offers:
                onFragmentReplace(new OffersFragment(), false);
                break;
            case R.id.navigation_profile:
                onFragmentReplace(new AccountFragment(), false);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                String fragName = foodMainFragment.getClass().getName().toString();
                if (foodMainFragment instanceof HomeFragment) {
                    fragmentManager.popBackStack(fragName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else {
                    fragmentManager.popBackStack();
                }
            } else {

                new AlertDialog.Builder(this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {


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
