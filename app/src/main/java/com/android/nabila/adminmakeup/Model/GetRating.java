package com.android.nabila.adminmakeup.Model;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRating {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<Rating> result = new ArrayList<Rating>();
    @SerializedName("message")
    private String message;

    public GetRating() {}

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Rating> getListDataRating() {
        return result;
    }

    public void setResult(List<Rating> result) {
        this.result = result;
    }

}