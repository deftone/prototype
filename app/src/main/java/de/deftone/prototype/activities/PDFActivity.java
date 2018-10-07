package de.deftone.prototype.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.deftone.prototype.R;

import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_EXERCISE_ID;
import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_EXERCISE_NAME;

public class PDFActivity extends AppCompatActivity implements OnClickListener {


    LinearLayout readLayout;

    // index to track currentPage in rendered Pdf
    private static int currentPage = 0;
    // View on which Pdf content will be rendered
    ImageView pdfView;

    // Currently rendered Pdf file
    String openedPdfFileName;
    Button generatePdf;
    Button next;
    Button previous;

    // File Descriptor for rendered Pdf file
    private ParcelFileDescriptor mFileDescriptor;
    // For rendering a PDF document
    private PdfRenderer mPdfRenderer;
    // For opening current page, render it, and close the page
    private PdfRenderer.Page mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        //get pdf file
        String pdfFileName = getIntent().getExtras().getString(EXTRA_EXERCISE_NAME);
        int pdfFileId = getIntent().getExtras().getInt(EXTRA_EXERCISE_ID);
        File file = new File(getFilesDir(), pdfFileName);
        try {
            InputStream inputStream = getResources().openRawResource(pdfFileId);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.close();
            inputStream.close();


            readLayout = findViewById(R.id.read_layout);
            next = findViewById(R.id.next);
            next.setOnClickListener(this);
            previous = findViewById(R.id.previous);
            previous.setOnClickListener(this);
            pdfView = findViewById(R.id.pdfView);

            //now open this file with renderer
            openRenderer(file.getPath());
            showPage(currentPage);
        } catch (Exception e) {
            System.out.println("there is a problem with opening the pdf file :C");
        }
    }


    /**
     * API to update ActionBar text
     */
    private void updateActionBarText() {
        int index = mCurrentPage.getIndex();
        int pageCount = mPdfRenderer.getPageCount();
        previous.setEnabled(0 != index);
        next.setEnabled(index + 1 < pageCount);
        if (getActionBar() != null) {
            getActionBar().setTitle(
                    openedPdfFileName + "(" + (index + 1) + "/" + pageCount
                            + ")");
        }
    }

    /**
     * Callback for handling view click events
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.next:
                currentPage++;
                showPage(currentPage);
                break;
            case R.id.previous:
                currentPage--;
                showPage(currentPage);
                break;
        }

    }

    /**
     * API for initializing file descriptor and pdf renderer after selecting pdf
     * from list
     *
     * @param filePath
     */
    private void openRenderer(String filePath) {
        File file = new File(filePath);
        try {
            mFileDescriptor = ParcelFileDescriptor.open(file,
                    ParcelFileDescriptor.MODE_READ_ONLY);
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * API for cleanup of objects used in rendering
     */
    private void closeRenderer() {

        try {
            if (mCurrentPage != null)
                mCurrentPage.close();
            if (mPdfRenderer != null)
                mPdfRenderer.close();
            if (mFileDescriptor != null)
                mFileDescriptor.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * API show to particular page index using PdfRenderer
     *
     * @param index
     */
    private void showPage(int index) {
        if (mPdfRenderer == null || mPdfRenderer.getPageCount() <= index
                || index < 0) {
            return;
        }
        // For closing the current page before opening another one.
        try {
            if (mCurrentPage != null) {
                mCurrentPage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Open page with specified index
        mCurrentPage = mPdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

        // Pdf page is rendered on Bitmap
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // Set rendered bitmap to ImageView
        pdfView.setImageBitmap(bitmap);
        updateActionBarText();
    }

}
