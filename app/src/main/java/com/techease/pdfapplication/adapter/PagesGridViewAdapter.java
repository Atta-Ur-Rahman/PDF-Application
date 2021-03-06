package com.techease.pdfapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.techease.pdfapplication.R;
import com.techease.pdfapplication.dataModel.DataModel;
import com.techease.pdfapplication.ui.fragment.SinglePagePdfFragment;
import com.techease.pdfapplication.utilities.GeneralUtils;

import java.io.File;
import java.util.ArrayList;

public class PagesGridViewAdapter extends BaseAdapter {
    ArrayList<DataModel> clientsDetailsModels;
    Context context;
    private LayoutInflater layoutInflater;
    MyViewHolder viewHolder = null;

    public PagesGridViewAdapter(Context context, ArrayList<DataModel> clientsDetailsModels) {
        this.clientsDetailsModels = clientsDetailsModels;
        this.context = context;
        if (context != null) {
            this.layoutInflater = LayoutInflater.from(context);

        }
    }

    @Override
    public int getCount() {
        if (clientsDetailsModels != null) return clientsDetailsModels.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (clientsDetailsModels != null && clientsDetailsModels.size() > position)
            return clientsDetailsModels.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        final DataModel model = clientsDetailsModels.get(position);
        if (clientsDetailsModels != null && clientsDetailsModels.size() > position)
            return clientsDetailsModels.size();
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DataModel model = clientsDetailsModels.get(position);

        viewHolder = new MyViewHolder();
        convertView = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        viewHolder.pdfView = convertView.findViewById(R.id.gv_pdf);
        viewHolder.tvPage = convertView.findViewById(R.id.tv_page);
        viewHolder.tvPdfTitle = convertView.findViewById(R.id.tv_pdf_title);

        final View finalConvertView = convertView;
        viewHolder.pdfView.fromFile(new File(GeneralUtils.getSharedPreferences(context).getString("path", "")))
                .pages(position)
                .defaultPage(position)
                .swipeHorizontal(false)
                .enableDoubletap(false)
                .enableSwipe(false)
                .enableAnnotationRendering(false)
                .password(null)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        viewHolder.ivProgress = finalConvertView.findViewById(R.id.iv_progress);
                        viewHolder.ivProgress.setVisibility(View.GONE);

                    }
                })
                .load();

        viewHolder.tvPdfTitle.setText("page " + String.valueOf(position + 1));
        viewHolder.tvPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putValueInEditor(context).putInt("current_page_number", position).commit();
                GeneralUtils.connectFragmentWithOutBackStack((Activity) context, new SinglePagePdfFragment());

            }
        });

        convertView.setTag(viewHolder);
        return convertView;
    }


    private class MyViewHolder {
        ImageView ivProgress;
        PDFView pdfView;
        TextView tvPage, tvPdfTitle;
    }







}
