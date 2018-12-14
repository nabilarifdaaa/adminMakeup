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
import com.bumptech.glide.Glide;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarEditProduk extends AppCompatActivity {
    ImageView mPhotoUrl;
    Context mContext;
    ImageView mImageView;
    Button btUpdate, btDelete, btBack, btPhotoUrl;
    EditText edtIdProduk, edtNamaProduk, edtJenis,edtHarga,edtDetail;
    TextView tvMessage;
    String pathImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_edit_produk);
        mPhotoUrl = (ImageView) findViewById(R.id.imgAddPhotoId);
        mContext = getApplicationContext();
        edtIdProduk= findViewById(R.id.edtIdProduk);
        edtNamaProduk=  findViewById(R.id.edtNama);
        edtJenis=  findViewById(R.id.edtJenis);
        edtHarga= findViewById(R.id.edtHarga);
        edtDetail= findViewById(R.id.edtDetail);

        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);
        btBack = (Button) findViewById(R.id.btBack);
        btPhotoUrl = (Button) findViewById(R.id.btAddPhotoId);

        tvMessage = findViewById(R.id.tvAddMessage);
        Intent mIntent = getIntent();

        if (mIntent.getStringExtra("photo_url") != null)
            Glide.with(mContext).load(ApiClient
                    .BASE_URL + mIntent.getStringExtra("photo_url")).into(mPhotoUrl);
        else
            Glide.with(mContext).load(R.drawable.default_user).into(mPhotoUrl);

        pathImage = mIntent.getStringExtra("photo_url");
        setListener();
        edtIdProduk.setText(mIntent.getStringExtra("idProduk"));
        edtNamaProduk.setText(mIntent.getStringExtra("namaProduk"));
        edtJenis.setText(mIntent.getStringExtra("jenisProduk"));
        edtHarga.setText(mIntent.getStringExtra("hargaProduk"));
        edtDetail.setText(mIntent.getStringExtra("detailProduk"));
    }
    private void setListener() {
        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MultipartBody.Part body = null;
                //dicek apakah image sama dengan yang ada di server atau berubah
                //jika sama dikirim lagi jika berbeda akan dikirim ke server
                if ((!pathImage.contains("uploads/" + edtIdProduk.getText().toString())) &&
                        (pathImage.length()>0)){
                    //File creating from selected URL
                    File file = new File(pathImage);

                    // create RequestBody instance from file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    body = MultipartBody.Part.createFormData("photo_url", file.getName(),
                            requestFile);
                }

                RequestBody reqIdProduk= MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtIdProduk.getText().toString().isEmpty())?"" : edtIdProduk.getText().toString());
                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtNamaProduk.getText().toString().isEmpty())?"":edtNamaProduk.getText().toString());
                RequestBody reqJenis = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtJenis.getText().toString().isEmpty())?"":edtJenis.getText().toString());
                RequestBody reqHarga = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtHarga.getText().toString().isEmpty())?"":edtHarga.getText().toString());
                RequestBody reqDetail = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtDetail.getText().toString().isEmpty())?"":edtDetail.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"), "update");

                Call<GetProduk> callUpdate = mApiInterface.putProduk(body, reqIdProduk, reqNama,
                        reqJenis,reqHarga, reqDetail, reqAction );

                callUpdate.enqueue(new Callback<GetProduk>() {
                    @Override
                    public void onResponse(Call<GetProduk> call, Response<GetProduk> response) {
                        //Log.d("Update Retrofit ", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            tvMessage.setText("Retrofit Update \n Status = " + response.body()
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
                            tvMessage.setText("Retrofit Update \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProduk> call, Throwable t) {
                        //Log.d("Update Retrofit ", t.getMessage());
                        tvMessage.setText("Retrofit Update \n Status = "+ t.getMessage());
                    }
                });

            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody reqIdProduk =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdProduk.getText().toString().isEmpty())?
                                        "" : edtIdProduk.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                Call<GetProduk> callDelete = mApiInterface.deleteProduk(reqIdProduk,reqAction);
                callDelete.enqueue(new Callback<GetProduk>() {
                    @Override
                    public void onResponse(Call<GetProduk> call, Response<GetProduk> response) {
                        tvMessage.setText("Retrofit Delete \n Status = "+response.body()
                                .getStatus() +"\n"+
                                "Message = "+response.body().getMessage()+"\n");
                    }

                    @Override
                    public void onFailure(Call<GetProduk> call, Throwable t) {
                        tvMessage.setText("Retrofit Delete \n Status = "+ t.getMessage());
                    }
                });
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tempIntent = new Intent(mContext, ListProduk.class);
                startActivity(tempIntent);
            }
        });

        btPhotoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);
                Intent intentChoose = Intent.createChooser(galleryIntent, "Pilih foto untuk " +
                        "di-upload");
                startActivityForResult(intentChoose, 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode ==10) {
            if (data == null) {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                pathImage = cursor.getString(columnIndex);

                //Picasso.with(mContext).load(new File(imagePath)).fit().into(mImageView);
                Glide.with(mContext).load(new File(pathImage)).into(mPhotoUrl);
                cursor.close();
            } else {
                Toast.makeText(mContext, "Foto gagal di-load", Toast.LENGTH_LONG).show();
            }
        }

    }
}
