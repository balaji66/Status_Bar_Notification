package com.durga.balaji66.status_bar_notification.Apis;

import com.durga.balaji66.status_bar_notification.Models.CandidateListModel;
import com.durga.balaji66.status_bar_notification.ResponseCategory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @GET("CandidateList")
    Call<ResponseCategory> CandidateList();


}


