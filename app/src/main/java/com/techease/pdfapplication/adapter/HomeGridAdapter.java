package com.techease.pdfapplication.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.techease.pdfapplication.R;
import com.techease.pdfapplication.dataModel.DataModel;
import com.techease.pdfapplication.ui.fragment.HomeFragment;
import com.techease.pdfapplication.ui.fragment.SinglePagePdfFragment;
import com.techease.pdfapplication.utilities.FileUtills;
import com.techease.pdfapplication.utilities.GeneralUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeGridAdapter extends BaseAdapter {
    ArrayList<DataModel> clientsDetailsModels;
    Context context;
    private LayoutInflater layoutInflater;
    MyViewHolder viewHolder = null;
    int pageNumber;
    public static ArrayList<String> alPdfPages = new ArrayList<>();

    List<String> list = HomeFragment.pdfFile;

    public HomeGridAdapter(Context context, ArrayList<DataModel> clientsDetailsModels) {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DataModel model = clientsDetailsModels.get(position);

        viewHolder = new MyViewHolder();
        convertView = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        viewHolder.pdfView = convertView.findViewById(R.id.gv_pdf);
        viewHolder.tvPage = convertView.findViewById(R.id.tv_page);
        viewHolder.tvPageNo = convertView.findViewById(R.id.tv_pdf_title);
        viewHolder.tvDate = convertView.findViewById(R.id.tv_date);
        viewHolder.tvSize = convertView.findViewById(R.id.tv_size);


        final String strPath = list.get(position);

        final View finalConvertView = convertView;
        viewHolder.pdfView.fromFile(new File(strPath))
                .pages(0)
                .defaultPage(0)
                .swipeHorizontal(false)
                .enableDoubletap(false)
                .enableSwipe(false)
                .enableAnnotationRendering(false)
                .password(null)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        alPdfPages.add(String.valueOf(pageCount));
                    }
                })
                .onLoad(new OnLoadCompleteListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void loadComplete(int nbPages) {

                        viewHolder.ivProgress = finalConvertView.findViewById(R.id.iv_progress);
                        viewHolder.ivProgress.setVisibility(View.GONE);
                    }
                })
                .load();

        String filename = new File(strPath).getName();
        viewHolder.tvPageNo.setText(FileUtills.getBaseName(filename));
        viewHolder.tvPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GeneralUtils.putValueInEditor(context).putInt("page_number", Integer.parseInt(alPdfPages.get(position))).commit();
                GeneralUtils.putValueInEditor(context).putString("path", strPath).commit();
                GeneralUtils.connectFragmentWithOutBackStack(context, new SinglePagePdfFragment());

            }
        });


        viewHolder.tvSize.setText(FileUtills.getFolderSizeLabel(new File(strPath)));
        viewHolder.tvDate.setText(FileUtills.getFileDataAndTime(new File(strPath)));
        convertView.setTag(viewHolder);
        return convertView;
    }


    private class MyViewHolder {
        ImageView ivProgress;
        PDFView pdfView;
        TextView tvPage, tvPageNo, tvDate, tvSize;
    }



}
