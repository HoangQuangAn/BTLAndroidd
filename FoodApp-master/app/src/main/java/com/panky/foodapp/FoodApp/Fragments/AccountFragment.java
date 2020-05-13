package com.panky.foodapp.FoodApp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.panky.foodapp.loginPageActivity.FaqsActivity;
import com.panky.foodapp.loginPageActivity.LoginResult;
import com.panky.foodapp.loginPageActivity.LoginSignUpActivity;
import com.panky.foodapp.R;
import com.panky.foodapp.loginPageActivity.SupportActivity;
import com.panky.foodapp.loginPageActivity.TermsAndConditionActivity;

public class AccountFragment extends Fragment implements View.OnClickListener{

    private View view;
    private LinearLayout supportLLyt, faqsLLyt, termsLLyt;
    private RelativeLayout loginLLyt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        initViews();

        loginLLyt.setOnClickListener(this);
        supportLLyt.setOnClickListener(this);
        faqsLLyt.setOnClickListener(this);
        termsLLyt.setOnClickListener(this);
        return view;
    }

    private void initViews() {
        loginLLyt = view.findViewById(R.id.loginLLyt);
        supportLLyt = view.findViewById(R.id.supportLLyt);
        faqsLLyt = view.findViewById(R.id.faqsLLyt);
        termsLLyt = view.findViewById(R.id.termsLLyt);
    }

    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-2).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.loginLLyt:
                startActivity(new Intent(getActivity(), LoginResult.class));
                break;

            case R.id.supportLLyt:
                startActivity(new Intent(getActivity(), SupportActivity.class));
                break;

            case R.id.faqsLLyt:
                startActivity(new Intent(getActivity(), FaqsActivity.class));
                break;

            case R.id.termsLLyt:
                startActivity(new Intent(getActivity(), TermsAndConditionActivity.class));
                break;
        }
    }
}
