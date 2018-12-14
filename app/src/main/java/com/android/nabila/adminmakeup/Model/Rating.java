package com.android.nabila.adminmakeup.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {
    public Rating(String idRating, String idProduk, String idUser, String rating, String review, String tanggal) {
        this.idRating = idRating;
        this.idProduk = idProduk;
        this.idUser = idUser;
        this.rating = rating;
        this.review = review;
        this.tanggal = tanggal;
    }

    @SerializedName("idRating")
    @Expose
    private String idRating;
    @SerializedName("idProduk")
    @Expose
    private String idProduk;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public String getIdRating() {
        return idRating;
    }

    public void setIdRating(String idRating) {
        this.idRating = idRating;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

}