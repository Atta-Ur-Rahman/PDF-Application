package com.techease.pdfapplication.utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.techease.pdfapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileUtills {

    public static String getBaseName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

    public static String getFolderSizeLabel(File file) {
        long size = getFolderSize(file) / 1024; // Get size and convert bytes into Kb.
        if (size >= 1024) {
            return (size / 1024) + " Mb";
        } else {
            return size + " Kb";
        }
    }

    public static long getFolderSize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                size += getFolderSize(child);
            }
        } else {
            size = file.length();
        }
        return size;
    }

    public static String getFileDataAndTime(File file) {
        Date lastModDate = new Date(file.lastModified());
        SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
        String date = format.format(lastModDate);
        return date;
    }


    public static boolean addImagesToPdf(String inputPath, String output, ArrayList<String> imagesUri) {
        try {
            PdfReader reader = new PdfReader(inputPath);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(output));
            Rectangle documentRect = document.getPageSize();
            document.open();

            int numOfPages = reader.getNumberOfPages();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage importedPage;
            for (int page = 1; page <= numOfPages; page++) {
                importedPage = writer.getImportedPage(reader, page);
                document.newPage();
                cb.addTemplate(importedPage, 0, 0);
            }

            for (int i = 0; i < imagesUri.size(); i++) {
                document.newPage();
                Image image = Image.getInstance(imagesUri.get(i));
                image.setBorder(0);
                float pageWidth = document.getPageSize().getWidth(); // - (mMarginLeft + mMarginRight);
                float pageHeight = document.getPageSize().getHeight(); // - (mMarginBottom + mMarginTop);
                image.scaleToFit(pageWidth, pageHeight);
                image.setAbsolutePosition(
                        (documentRect.getWidth() - image.getScaledWidth()) / 2,
                        (documentRect.getHeight() - image.getScaledHeight()) / 2);
                document.add(image);
            }

            document.close();

//            getSnackbarwithAction(mContext, R.string.snackbar_pdfCreated)
//                    .setAction(R.string.snackbar_viewAction, v -> mFileUtils.openFile(output)).show();
//            new DatabaseHelper(mContext).insertRecord(output, mContext.getString(R.string.created));

            return true;
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
//            showSnackbar(mContext, R.string.remove_pages_error);
            return false;
        }
    }

}
