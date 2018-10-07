package com.durga.balaji66.status_bar_notification.Models;

import com.google.gson.annotations.SerializedName;

public class CandidateListModel {
    @SerializedName("UId")
    private String mUId;
    @SerializedName("Name")
    private String mName;
    @SerializedName("Phone")
    private String mPhone;

    public String getmUId() {
        return mUId;
    }

    public void setmUId(String mUId) {
        this.mUId = mUId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
