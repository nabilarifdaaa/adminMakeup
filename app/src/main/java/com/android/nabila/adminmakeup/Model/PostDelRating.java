package com.android.nabila.adminmakeup.Model;

import com.google.gson.annotations.SerializedName;

public class PostDelRating {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    Rating mRating;
    @SerializedName("message")
    String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rating getmRating() {
        return mRating;
    }

    public void setmRating(Rating rating) {
        mRating = rating;
    }

}
