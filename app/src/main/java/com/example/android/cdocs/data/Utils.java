package com.example.android.cdocs.data;

import android.content.Context;
import android.os.Environment;

import com.example.android.cdocs.base.BaseActivity;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;

public class Utils {

    private static Utils sUtils;
    // Weak Reference to avoid memory leaks
    private static WeakReference<Context> weakReference;

    /**
     * Singleton
     *
     * @param context App Context
     * @return Utils object
     */
    public static Utils getInstance(Context context) {
        if (sUtils == null) {
            sUtils = new Utils();
            weakReference = new WeakReference<>(context);
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
    public boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                File file = new File(weakReference.get().getApplicationContext().getFilesDir(),
                        fileName);
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

                return true;
            } catch (IOException e) {
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
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), fileName);
            return file;
        }
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName);
    }
}
