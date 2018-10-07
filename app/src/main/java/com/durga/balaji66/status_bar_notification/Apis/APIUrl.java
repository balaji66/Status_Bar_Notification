package com.durga.balaji66.status_bar_notification.Apis;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUrl {
    /* Global Variables */
    private static final String BASE_URL = "https://squareandcube.000webhostapp.com/AptitudePreparation/public/";
    private static APIUrl mInstance;
    private static Retrofit retrofit=null;

    /**
     * This is for checking the connection timeout and writeTimeout, readTimeout.
     * @return
     */
    private OkHttpClient getRequestHeader() {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Here we are creating the object to the Retrofit 2
     * we are considering the GsonConverterFactory to convert the json Object.
     */
    private  APIUrl()
    {
         retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    /**
     * Here we are instantiating the class object.
     * @return
     */
    public static synchronized APIUrl getmInstance()
    {
        if(mInstance == null)
        {
            mInstance =new APIUrl();
        }
        return mInstance;
    }

    public ApiService getApi()
    {
        return retrofit.create(ApiService.class);
    }


}
