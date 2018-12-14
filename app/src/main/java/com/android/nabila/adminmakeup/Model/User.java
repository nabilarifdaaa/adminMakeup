package com.android.nabila.adminmakeup.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("idUser")
    private String id_user;
    @SerializedName("nama")
    private String nama;
    @SerializedName("umur")
    private String umur;
    @SerializedName("jenisKulit")
    private String jenis_kulit;
    @SerializedName("warnaKulit")
    private String warna_kulit;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("photo_url_user")
    private String photoUrlUser;
    private String action;

    public User(String id_user, String nama, String umur, String jenis_kulit, String warna_kulit, String username, String password, String photoUrlUser, String action) {
        this.id_user = id_user;
        this.nama = nama;
        this.umur = umur;
        this.jenis_kulit = jenis_kulit;
        this.warna_kulit = warna_kulit;
        this.username = username;
        this.password = password;
        this.photoUrlUser = photoUrlUser;
        this.action = action;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getJenis_kulit() {
        return jenis_kulit;
    }

    public void setJenis_kulit(String jenis_kulit) {
        this.jenis_kulit = jenis_kulit;
    }

    public String getWarna_kulit() {
        return warna_kulit;
    }

    public void setWarna_kulit(String warna_kulit) {
        this.warna_kulit = warna_kulit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoUrlUser() {
        return photoUrlUser;
    }

    public void setPhotoUrlUser(String photoUrlUser) {
        this.photoUrlUser = photoUrlUser;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
