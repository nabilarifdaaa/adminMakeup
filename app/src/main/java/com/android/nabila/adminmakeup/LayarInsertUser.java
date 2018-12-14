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

import com.android.nabila.adminmakeup.MenuDashboard;
import com.android.nabila.adminmakeup.Model.GetUser;
import com.android.nabila.adminmakeup.R;
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

public class LayarInsertUser extends AppCompatActivity {

    Context mContext;
    ImageView mImageView;
    Button btAddPhotoId, btAddBack, btAddData;
    EditText edtAddIdUser, edtAddNamaUser, edtAddUmur,edtAddJenis,edtAddWarna,edtAddUsername,edtAddPassword;
    TextView tvAddMessage;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_insert_user);

        mContext = getApplicationContext();
        mImageView = findViewById(R.id.imgAddPhotoId);
        btAddPhotoId =  findViewById(R.id.btAddPhotoId);
        edtAddNamaUser =  findViewById(R.id.edtAddNamaUser);
        edtAddUmur = findViewById(R.id.edtAddUmur);
        edtAddJenis =  findViewById(R.id.edtAddJenis);
        edtAddWarna = findViewById(R.id.edtAddWarna);
        edtAddUsername = findViewById(R.id.edtAddUsername);
        edtAddPassword = findViewById(R.id.edtAddPassword);

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
                        (edtAddNamaUser.getText().toString().isEmpty())?"":edtAddNamaUser.getText().toString());
                RequestBody reqUmur = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddUmur.getText().toString().isEmpty())?"":edtAddUmur.getText().toString());
                RequestBody reqJenis = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddJenis.getText().toString().isEmpty())?"":edtAddJenis.getText().toString());
                RequestBody reqWarna = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddWarna.getText().toString().isEmpty())?"":edtAddWarna.getText().toString());
                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddUsername.getText().toString().isEmpty())?"":edtAddUsername.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtAddPassword.getText().toString().isEmpty())?"":edtAddPassword.getText().toString());
                RequestBody reqAction = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        "insert");
                Call<GetUser> mUserCall = mApiInterface.postUser(body, reqNama, reqUmur, reqJenis,
                        reqWarna, reqUsername, reqPassword, reqAction );
                mUserCall.enqueue(new Callback<GetUser>() {
                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        if (response.body().getStatus().equals("failed")){
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "idUser = "+response.body().getResult().get(0).getId_user()+"\n"+
                                    "nama = "+response.body().getResult().get(0).getNama()+"\n"+
                                    "umur = "+response.body().getResult().get(0).getUmur()+"\n"+
                                    "jenisKulit = "+response.body().getResult().get(0).getJenis_kulit()+"\n"+
                                    "warnaKulit = "+response.body().getResult().get(0).getWarna_kulit()+"\n"+
                                    "username = "+response.body().getResult().get(0).getUsername()+"\n"+
                                    "password = "+response.body().getResult().get(0).getPassword()+"\n"+
                                    "photo_url_user = "+response.body().getResult().get(0).getPhotoUrlUser()
                                    +"\n";
                            tvAddMessage.setText("Retrofit Insert \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        tvAddMessage.setText("Retrofit Insert Failure \n Status = "+ t.getMessage
                                ());
                    }

                });
            }
        });
        btAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ListUser.class);
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
