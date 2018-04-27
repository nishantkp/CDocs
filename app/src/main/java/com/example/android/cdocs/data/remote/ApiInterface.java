package com.example.android.cdocs.data.remote;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {

    /**
     * Callback method for getting file from provided url
     *
     * @param url Url of file in which we are interested in
     * @return ResponseBody object
     */
    @Streaming
    @GET()
    Call<ResponseBody> downloadFile(@Url String url);

    @Streaming
    @GET()
    Observable<ResponseBody> downloadFileRx(@Url String url);
}
