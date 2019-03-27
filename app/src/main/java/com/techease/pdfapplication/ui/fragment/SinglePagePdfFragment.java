package com.techease.pdfapplication.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.techease.pdfapplication.R;
import com.techease.pdfapplication.utilities.GeneralUtils;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class SinglePagePdfFragment extends Fragment {

    View parentView;


    PDFView pdfView;
    TextView tvPageNumber;
    ImageView ivProgress, ivGridView;
    int coutnPages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_pdf_view, container, false);
        setHasOptionsMenu(true);
        initDeclation();
        initUI();
        onBack();

        ivGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.connectFragmentWithOutBackStack(getActivity(), new GridViewPdfFragment());
            }
        });
        return parentView;
    }

    private void initDeclation() {
        pdfView = parentView.findViewById(R.id.pdfView);
        tvPageNumber = parentView.findViewById(R.id.tv_page_number);
        ivProgress = parentView.findViewById(R.id.iv_progress);
        ivGridView = parentView.findViewById(R.id.iv_grid_view);
    }

    private void initUI() {

        pdfView.fromFile(new File(GeneralUtils.getSharedPreferences(getActivity()).getString("path", "")))
                .enableSwipe(true) // allows to block changing pages using swipe
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .enableSwipe(true)
                .defaultPage(GeneralUtils.getSharedPreferences(getActivity()).getInt("current_page_number", 0))
                .autoSpacing(true)
                .pageFling(true)
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                .password(null)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Log.d("pagecount", String.valueOf(pageCount));
                        GeneralUtils.putValueInEditor(getActivity()).putInt("page_number", pageCount).commit();

                        tvPageNumber.setText("Page " + String.valueOf(page + 1) + " of " + String.valueOf(pageCount));

                    }
                })
                .scrollHandle(new ScrollHandle() {
                    @Override
                    public void setScroll(float position) {

                    }

                    @Override
                    public void setupLayout(PDFView pdfView) {

                    }

                    @Override
                    public void destroyLayout() {

                    }

                    @Override
                    public void setPageNum(int pageNum) {

                    }

                    @Override
                    public boolean shown() {
                        return false;
                    }

                    @Override
                    public void show() {

                    }

                    @Override
                    public void hide() {

                    }

                    @Override
                    public void hideDelayed() {

                    }
                })
                .enableAntialiasing(false) // improve rendering a little bit on low-res screens
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        ivProgress.setVisibility(View.GONE);
                    }
                })
                .spacing(0)
                .load();

        pdfView.zoomTo(1);


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
}
