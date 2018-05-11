package com.example.android.cdocs.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;

import com.example.android.cdocs.ui.model.Docs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;

class Utils {

    private static Utils sUtils;
    // Weak Reference to avoid memory leaks
    // private static WeakReference<Context> weakReference;
    private static boolean fileWrittenFlag = false;
    private static File baseFileLocation;

    private static int pageCount = 0;

    /**
     * Singleton
     *
     * @param context App Context
     * @return Utils object
     */
    static Utils getInstance(Context context) {
        if (sUtils == null) {
            sUtils = new Utils();
            // weakReference = new WeakReference<>(context);
            // Get the default base file location. So whenever we want to access the file location,
            // we just have to append the file name to it
            baseFileLocation = context.getFilesDir();
        }
        return sUtils;
    }

    /**
     * Android boiler-plate code write date into internal/external file storage.
     * If you want to store data into external storage call getPublicFileStorageDir() method to get
     * the file directory
     *
     * @param body     ResponseBody object received from making network call
     * @param fileName Name of the file
     * @return true if the file is saved into external file and false if it's not
     */
    boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        if (body == null) {
            return false;
        }
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                File file = new File(baseFileLocation,
                        fileName + ".pdf");
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                }

                outputStream.flush();
                fileWrittenFlag = true;
                return true;
            } catch (IOException e) {
                fileWrittenFlag = false;
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            fileWrittenFlag = false;
            return false;
        }
    }

    /**
     * Method to get the file if user has external storage attached to device i.e USB or SD card
     *
     * @param fileName name of the file
     * @return File object indication location where you want to store object
     */
    private File getPublicFileStorageDir(String fileName) {
        // Get the directory for the user's public documents directory.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            return new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), fileName);
        }
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName);
    }

    /**
     * Method to generate the correct file path from file Title
     *
     * @param docs Docs object which contains title of file and needed to generate correct file path
     * @return file path
     */
    private File getFilePath(Docs docs) {
        return new File(
                baseFileLocation
                        + "/" + docs.getTitle() + ".pdf");
        }

    /**
     * Used to get the Render of pdf, This method is not thread safe
     * If you want to render PDF, create a Renderer -> open a page -> render it -> close the page
     * Once you close the renderer it should not be used any more
     *
     * @param docs Docs object containing file name
     * @return PdfRenderer
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private synchronized PdfRenderer getPage(Docs docs) {
        if (!fileWrittenFlag) {
            return null;
        }

        // filePath represent path of Pdf document on storage
        File file = getFilePath(docs);
        // FileDescriptor for file, it allows you to close file when you are
        // done with it
        ParcelFileDescriptor mFileDescriptor = null;
        PdfRenderer mPdfRenderer = null;
        try {
            mFileDescriptor = ParcelFileDescriptor.open(file,
                    ParcelFileDescriptor.MODE_READ_ONLY);
            // PdfRenderer enables rendering a PDF document
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
            // mFileDescriptor.close();
        } catch (IOException e) {
            return null;
        }
        return mPdfRenderer;
    }

    /**
     * Generate a Bitmap of previous page
     *
     * @param docs Docs object containing file name
     * @return Bitmap of previous page
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    Bitmap getPreviousPage(Docs docs) {
        PdfRenderer pdfRenderer = getPage(docs);
        if (pdfRenderer == null) return null;
        pageCount = pageCount > 0 ? pageCount - 1 : 0;
        PdfRenderer.Page mCurrentPage = pdfRenderer.openPage(pageCount);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        mCurrentPage.close();
        return bitmap;
    }

    /**
     * Generate a Bitmap of next page
     *
     * @param docs Docs object containing a file name
     * @return Bitmap of previous page
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    Bitmap getNextPage(Docs docs) {
        PdfRenderer pdfRenderer = getPage(docs);
        if (pdfRenderer == null) return null;
        pageCount = pageCount < pdfRenderer.getPageCount() - 1 ? pageCount + 1 : pdfRenderer.getPageCount() - 1;
        PdfRenderer.Page mCurrentPage = pdfRenderer.openPage(pageCount);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        mCurrentPage.close();
        return bitmap;
    }

    /**
     * Generate a Bitmap of first page
     *
     * @param docs Docs object containing a file name
     * @return Bitmap of a previous page
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    Bitmap getFirstPage(Docs docs) {
        PdfRenderer pdfRenderer = getPage(docs);
        if (pdfRenderer == null) return null;
        PdfRenderer.Page mCurrentPage = pdfRenderer.openPage(0);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        mCurrentPage.close();
        return bitmap;
    }
}
