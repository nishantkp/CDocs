package com.example.android.cdocs.data;

import android.content.Context;

import com.example.android.cdocs.data.local.DatabaseHelper;
import com.example.android.cdocs.data.remote.ApiClient;
import com.example.android.cdocs.data.remote.ApiInterface;
import com.example.android.cdocs.ui.model.Docs;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class DataManager {
    private static DataManager sDataManager;
    private static PreferenceHelper sPreferenceHelper;
    private static DatabaseHelper sDatabaseHelper;
    private static ApiInterface sApiInterface;
    private static Utils sUtils;

    public static DataManager getInstance(Context context) {
        if (sDataManager == null) {
            sDataManager = new DataManager();
            sPreferenceHelper = PreferenceHelper.getInstance(context);
            sDatabaseHelper = DatabaseHelper.getInstance(context);
            sApiInterface = ApiClient.getClient().create(ApiInterface.class);
            sUtils = Utils.getInstance(context);
        }
        return sDataManager;
    }

    // Write data to SharedPreference
    public void writeDataToPreference(String key, String value) {
        sPreferenceHelper.writeString(key, value);
    }

    // Read data from SharedPreference
    public String readDataFromPreference(String key) {
        return sPreferenceHelper.readString(key);
    }

    // Insert data into database table
    public void insertDataToDatabase(List<Docs> docsList) {
        sDatabaseHelper.insertDocuments(docsList);
    }

    // Insert single item into database
    public void insertSingleItemToDatabase(Docs docs) {
        sDatabaseHelper.inserSIngleItem(docs);
    }

    // Read data from database
    public List<Docs> readDataFromDatabase() {
        return sDatabaseHelper.readDocumentList();
    }

    // Delete all the data from database table
    public void deleteDataFromDatabase() {
        sDatabaseHelper.deleteAll();
    }

    // Clear preferences when user clicks on logout
    public void logout() {
        sPreferenceHelper.clearPreferences();
    }

    public Call<ResponseBody> downloadFileFromUrl(String url) {
        return sApiInterface.downloadFile(url);
    }

    /**
     * Use retrofit to download file
     *
     * @param url url of file from where you want to download it
     * @return Observable object of ResponseBody
     */
    public Observable<ResponseBody> downloadFileFromUrlRx(String url) {
        return sApiInterface.downloadFileRx(url).flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
            @Override
            public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Exception {
                return Observable.just(responseBody);
            }
        });
    }

    /**
     * Use this method to write data into devices internal-storage on
     * .io() thread by Rx
     *
     * @param responseBody ResponseBody object of okHttp
     */
    public void writeDataToDisk(final ResponseBody responseBody) {
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return sUtils.writeResponseBodyToDisk(responseBody);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}