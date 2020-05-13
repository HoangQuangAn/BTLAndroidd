package com.panky.foodapp.View.Category;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.panky.foodapp.R;
import com.panky.foodapp.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
//    private LinearLayout layoutBottomSheet;
//    private BottomSheetBehavior sheetBehavior;
//    private Button btnBottomSheet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_category, container, false);

        initViews();
        cardCategory.setOnClickListener(this);

//        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
       /* sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });*/
        return view;
    }

    private void initViews() {
        imgVCategory = view.findViewById(R.id.imageCategory);
        imgVBGCategory = view.findViewById(R.id.imageCategoryBg);
        tvCategory = view.findViewById(R.id.textCategory);
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);
        pbLoadingData = view.findViewById(R.id.progressBar);
        cardCategory = view.findViewById(R.id.cardCategory);

//        layoutBottomSheet = view.findViewById(R.id.bottom_sheet);
//        btnBottomSheet = layoutBottomSheet.findViewById(R.id.btnBottomSheet);
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
                Toast.makeText(getActivity(), "Meal :: "+meals.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
//                toggleBottomSheet();
            }
        });

    }

    /*public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//            btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            btnBottomSheet.setText("Expand sheet");
        }
    }*/

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
//               dialog.show();
            }
        });

        descDialog.show();
    }
}