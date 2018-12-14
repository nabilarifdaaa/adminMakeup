package com.android.nabila.adminmakeup.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetProduk {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private List<Produk> result =  new ArrayList<Produk>();
    @SerializedName("message")
    private String message;
    public GetProduk() {}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Produk> getResult() {
        return result;
    }

    public void setResult(List<Produk> result) {
        this.result = result;
    }
    public String getMessage() {
        return message;
    }
}
