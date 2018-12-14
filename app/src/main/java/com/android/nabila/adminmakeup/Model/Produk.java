package com.android.nabila.adminmakeup.Model;

import com.google.gson.annotations.SerializedName;

public class Produk {
    @SerializedName("idProduk")
    private String idProduk;
    @SerializedName("namaProduk")
    private String namaProduk;
    @SerializedName("jenisProduk")
    private String jenisProduk;
    @SerializedName("hargaProduk")
    private String hargaProduk;
    @SerializedName("detailProduk")
    private String detailProduk;
    @SerializedName("photo_url")
    private String photoUrl;
    private String action;

    public Produk(String idProduk, String namaProduk, String jenisProduk, String hargaProduk, String detailProduk, String photoUrl, String action) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.jenisProduk = jenisProduk;
        this.hargaProduk = hargaProduk;
        this.detailProduk = detailProduk;
        this.photoUrl = photoUrl;
        this.action = action;
    }

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getJenisProduk() {
        return jenisProduk;
    }

    public void setJenisProduk(String jenisProduk) {
        this.jenisProduk = jenisProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getDetailProduk() {
        return detailProduk;
    }

    public void setDetailProduk(String detailProduk) {
        this.detailProduk = detailProduk;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
