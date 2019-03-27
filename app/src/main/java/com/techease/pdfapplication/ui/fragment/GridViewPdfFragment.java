package com.techease.pdfapplication.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.techease.pdfapplication.R;
import com.techease.pdfapplication.dataModel.DataModel;
import com.techease.pdfapplication.adapter.PagesGridViewAdapter;
import com.techease.pdfapplication.utilities.GeneralUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridViewPdfFragment extends Fragment {


    View parentView;
    GridView gridView;
    ArrayList<DataModel> list;
    PagesGridViewAdapter adapter;
    ImageView ivSinglePage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_grid_view_pdf_file, container, false);

//        customActionBar();
        setHasOptionsMenu(true);
        gridView = parentView.findViewById(R.id.gridview_images);
        ivSinglePage = parentView.findViewById(R.id.iv_single_page);
        ivSinglePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithOutBackStack(getActivity(),new SinglePagePdfFragment());
            }
        });
        initUI();
        onBack();
        return parentView;
    }

    private void onBack() {
        parentView.setFocusableInTouchMode(true);
        parentView.requestFocus();
        parentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //  Log.i(tag, "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //   Log.i(tag, "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    GeneralUtils.connectFragmentWithOutBackStack(getActivity(), new HomeFragment());
                    return true;
                }
                return false;
            }
        });
    }


    private void initUI() {
        list = new ArrayList<>();
        adapter = new PagesGridViewAdapter(getActivity(), list);
        gridView.setAdapter(adapter);


        DataModel dataModel = new DataModel();
        for (int i = 1; i <= GeneralUtils.getSharedPreferences(getActivity()).getInt("page_number", 0); i++) {
            dataModel.setPage_id(i);
            list.add(dataModel);
            adapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item1 = menu.findItem(R.id.action_grid_view);
//        item1.setVisible(false);
//
//        MenuItem itemHome = menu.findItem(R.id.action_home);
//        itemHome.setVisible(true);
//    }

    public void customActionBar() {
        android.support.v7.app.ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        ImageView ivSinglePages = mCustomView.findViewById(R.id.iv_single_page);
        ImageView ivGridViewPages = mCustomView.findViewById(R.id.iv_grid_view);


        ivSinglePages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithOutBackStack(getActivity(), new SinglePagePdfFragment());
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.show();
    }

}
