package com.durga.balaji66.status_bar_notification;

import com.durga.balaji66.status_bar_notification.Models.CandidateListModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCategory {

    public List<CandidateListModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CandidateListModel> categories) {
        this.categories = categories;
    }

    @SerializedName("CandidateList")
    @Expose
    private List<CandidateListModel> categories = null;

}
