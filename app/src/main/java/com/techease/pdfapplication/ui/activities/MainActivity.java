package com.techease.pdfapplication.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.techease.pdfapplication.R;
import com.techease.pdfapplication.ui.fragment.GridViewPdfFragment;
import com.techease.pdfapplication.ui.fragment.HomeFragment;
import com.techease.pdfapplication.ui.fragment.ImageToPdfFragment;
import com.techease.pdfapplication.ui.fragment.SinglePagePdfFragment;
import com.techease.pdfapplication.utilities.GeneralUtils;
import com.techease.pdfapplication.utilities.PermissionUtills;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtills.isStoragePermissionGranted(this);
        GeneralUtils.connectFragmentWithOutBackStack(this, new ImageToPdfFragment());

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grid_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection


        if (item.getItemId() == android.R.id.home) {

        }
        switch (item.getItemId()) {
            case R.id.action_grid_view:

                GeneralUtils.connectFragmentWithOutBackStack(this, new GridViewPdfFragment());

                break;
            case R.id.action_one_page_view:

                GeneralUtils.connectFragmentWithOutBackStack(this, new SinglePagePdfFragment());

                break;
            case R.id.action_home:

                GeneralUtils.connectFragmentWithOutBackStack(this, new HomeFragment());


            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

*/
}

