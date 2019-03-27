package com.techease.pdfapplication.ui.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.techease.pdfapplication.R;
import com.techease.pdfapplication.dataModel.DataModel;
import com.techease.pdfapplication.adapter.HomeGridAdapter;
import com.techease.pdfapplication.utilities.GeneralUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public static List<String> pdfFile = new ArrayList<>();

    View parentView;
    GridView gridView;
    ArrayList<DataModel> list;
    HomeGridAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_home, container, false);

        HomeGridAdapter.alPdfPages.clear();
        pdfFile.clear();
        setHasOptionsMenu(true);
        gridView = parentView.findViewById(R.id.gridview_images);
        walkdir(Environment.getExternalStorageDirectory());
        GeneralUtils.putValueInEditor(getActivity()).putInt("current_page_number", 0).commit();
        initUI();


        return parentView;
    }


    /*public static void main(String arg[]) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("YourPDFHere.pdf"));
        document.open();
        Image image = Image.getInstance("yourImageHere.jpg");
        document.add(new Paragraph("Your Heading for the Image Goes Here"));
        document.add(image);
        document.close();
    }*/

    private void initUI() {
        list = new ArrayList<>();
        adapter = new HomeGridAdapter(getActivity(), list);
        gridView.setAdapter(adapter);

        DataModel dataModel = new DataModel();
        for (int i = 1; i <= pdfFile.size(); i++) {
            dataModel.setPage_id(i);
            list.add(dataModel);
            adapter.notifyDataSetChanged();
        }

    }

    public void walkdir(File dir) {
        String pdfPattern = ".pdf";

        File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    walkdir(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(pdfPattern)) {
                        //Do what ever u want


                        String string = String.valueOf(listFile[i]);

                        pdfFile.add(string);
                        File file = new File(string);

                        Log.d("test", String.valueOf(listFile[i]));
                    }
                }
            }

        }

    }

//
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.action_one_page_view);
//        item.setVisible(false);
//        MenuItem item1 = menu.findItem(R.id.action_grid_view);
//        item1.setVisible(false);
//        MenuItem itemHome = menu.findItem(R.id.action_home);
//        itemHome.setVisible(false);
//    }
}
