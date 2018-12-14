package com.android.nabila.adminmakeup.Rest;

import com.android.nabila.adminmakeup.Model.GetProduk;
import com.android.nabila.adminmakeup.Model.GetRating;
import com.android.nabila.adminmakeup.Model.GetUser;
import com.android.nabila.adminmakeup.Model.PostDelRating;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    /********* USER *********/
    @GET("user/user")
    Call<GetUser> getUser();

    @Multipart
    @POST("user/user")
    Call<GetUser> postUser(
            @Part MultipartBody.Part file,
            @Part("nama") RequestBody nama,
            @Part("umur") RequestBody umur,
            @Part("jenisKulit") RequestBody jenisKulit,
            @Part("warnaKulit") RequestBody warnaKulit,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("user/user")
    Call<GetUser> putUser(
            @Part MultipartBody.Part file,
            @Part("idUser") RequestBody idUser,
            @Part("nama") RequestBody nama,
            @Part("umur") RequestBody umur,
            @Part("jenisKulit") RequestBody jenisKulit,
            @Part("warnaKulit") RequestBody warnaKulit,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("user/user")
    Call<GetUser> deleteUser(
            @Part("idUser") RequestBody idUser,
            @Part("action") RequestBody action);

    /********* PRODUK *********/
    @GET("produk/produk")
    Call<GetProduk> getProduk();

    @Multipart
    @POST("produk/produk")
    Call<GetProduk> postProduk(
            @Part MultipartBody.Part file,
            @Part("namaProduk") RequestBody namaProduk,
            @Part("jenisProduk") RequestBody jenisProduk,
            @Part("hargaProduk") RequestBody hargaProduk,
            @Part("detailProduk") RequestBody detailProduk,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("produk/produk")
    Call<GetProduk> putProduk(
            @Part MultipartBody.Part file,
            @Part("idProduk") RequestBody idProduk,
            @Part("namaProduk") RequestBody namaProduk,
            @Part("jenisProduk") RequestBody jenisProduk,
            @Part("hargaProduk") RequestBody hargaProduk,
            @Part("detailProduk") RequestBody detailProduk,
            @Part("action") RequestBody action
    );

    @Multipart
    @POST("produk/produk")
    Call<GetProduk> deleteProduk(
            @Part("idProduk") RequestBody idProduk,
            @Part("action") RequestBody action);
    //RATING
    @GET("rating/rating")
    Call<GetRating> getRating();

    @FormUrlEncoded
    @POST("rating/rating")
    Call<GetRating> postRating
            (
             @Field("idProduk") String idProduk,
             @Field("idUser") String idUser,
             @Field("rating") String rating,
             @Field("review") String review,
             @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "rating/rating", hasBody = true)
    Call<PostDelRating> deleteRating(@Field("idRating") String idRating);
}
