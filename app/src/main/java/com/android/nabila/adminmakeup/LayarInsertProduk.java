package com.android.nabila.adminmakeup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nabila.adminmakeup.Model.GetProduk;
import com.android.nabila.adminmakeup.Model.GetUser;
import com.android.nabila.adminmakeup.Rest.ApiClient;
import com.android.nabila.adminmakeup.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarInsertProduk extends AppCompatActivity {
    Context mContext;
    ImageView mImageView;
    Button btAddPhotoId, btAddBack, btAddData;
    EditText edtAddNama,edtAddJenis,edtAddHarga,edtAddDetail;
    TextView tvAddMessage;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_insert_produk);
        mContext = getApplicationContext();
        mImageView = findViewById(R.id.imgAddPhotoId);
        btAddPhotoId =  findViewById(R.id.btAddPhotoId);

        edtAddNama =  findViewById(R.id.edtAddNamaProduk);
        edtAddJenis =  findViewById(R.id.edtJenis);
        edtAddHarga = findViewById(R.id.edtAddHarga);
        edtAddDetail = findViewById(R.id.edtAddDetail);

        btAddData = findViewById(R.id.btAddData);
        btAddBack =  findViewById(R.id.btAddBack);
        tvAddMessage = findViewById(R.id.tvAddMessage);

        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

                MultipartBody.Part body = null;
                if (!imagePath.isEmpty()){
                    // Buat file dari image yang dipilih
                    File file = new File(imagePath);

                    // Buat RequestBody instance dari file
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

                    // MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody.Part.createFormData("photo_url", file.getName(),
                            requestFile);
                }
                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddNama.getText().toString().isEmpty())?"":edtAddNama.getText().toString());
                RequestBody reqJenis = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddJenis.getText().toString().isEmpty())?"":edtAddJenis.getText().toString());
                RequestBody reqHarga = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddHarga.getText().toString().isEmpty())?"":edtAddHarga.getText().toString());
                RequestBody reqDetail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddDetail.getText().toString().isEmpty())?"":edtAddDetail.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");

                Call<GetProduk> mProdukCall = mApiInterface.postProduk(body, reqNama,
                        reqJenis,reqHarga, reqDetail,reqAction );
                mProdukCall .enqueue(new Callback<GetProduk>() {
                    @Override
                    public void onResponse(Call<GetProduk> call, Response<GetProduk> response) {
//                      Log.d("Insert Retrofit",response.body().getMessage());
                        if (response.body().getStatus().equals("failed")){
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "idProduk = "+response.body().getResult().get(0).getIdProduk()+"\n"+
                                    "namaProduk = "+response.body().getResult().get(0).getNamaProduk()+"\n"+
                                    "jenisProduk = "+response.body().getResult().get(0).getJenisProduk()+"\n"+
                                    "hargaProduk = "+response.body().getResult().get(0).getHargaProduk()+"\n"+
                                    "detailProduk = "+response.body().getResult().get(0).getDetailProduk()+"\n"+
                                    "photo_url = "+response.body().getResult().get(0).getPhotoUrl()
                                    +"\n";
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProduk> call, Throwable t) {
//                      Log.d("Insert Retrofit", t.getMessage());
                        tvAddMessage.setText("Retrofit Insert Failure \n Status = "+ t.getMessage
                                ());
                    }
                });
            }
        });

        btAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListProduk.class);
                startActivity(intent);
            }
        });
        btAddPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(
                        galleryIntent,
                        "Pilih foto untuk di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10){
            if (data==null){
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath =cursor.getString(columnIndex);

                Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
//                Glide.with(mContext).load(new File(imagePath)).into(mImageView);
                cursor.close();
            }else{
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }
    }
}
