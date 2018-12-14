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

import com.android.nabila.adminmakeup.Model.GetUser;
import com.android.nabila.adminmakeup.R;
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

public class LayarEditUser extends AppCompatActivity {
    ImageView mPhotoUrl;
    Context mContext;
    ImageView mImageView;
    Button btUpdate, btDelete, btBack, btPhotoUrl;
    EditText edtIdUser, edtNamaUser, edtUmur,edtJenis,edtWarna,edtUsername,edtPassword;
    TextView tvMessage;
    String pathImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_edit_user);

        mPhotoUrl = (ImageView) findViewById(R.id.imgAddPhotoId);
        mContext = getApplicationContext();
        edtIdUser= findViewById(R.id.edtIdPembeli);
        edtNamaUser=  findViewById(R.id.edtAddNamaUser);
        edtUmur= findViewById(R.id.edtAddUmur);
        edtJenis=  findViewById(R.id.edtAddJenis);
        edtWarna= findViewById(R.id.edtAddWarna);
        edtUsername= findViewById(R.id.edtAddUsername);
        edtPassword= findViewById(R.id.edtAddPassword);

        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);
        btBack = (Button) findViewById(R.id.btBack);
        btPhotoUrl = (Button) findViewById(R.id.btAddPhotoId);

        tvMessage = findViewById(R.id.tvAddMessage);

        Intent mIntent = getIntent();

        edtIdUser.setText(mIntent.getStringExtra("idUser"));
        edtNamaUser.setText(mIntent.getStringExtra("nama"));
        edtUmur.setText(mIntent.getStringExtra("umur"));
        edtJenis.setText(mIntent.getStringExtra("jenisKulit"));
        edtWarna.setText(mIntent.getStringExtra("warnaKulit"));
        edtUsername.setText(mIntent.getStringExtra("username"));
        edtPassword.setText(mIntent.getStringExtra("password"));

        if (mIntent.getStringExtra("photo_url") != null)
            Glide.with(mContext).load(ApiClient
                    .BASE_URL + mIntent.getStringExtra("photo_url")).into(mPhotoUrl);
        else
            Glide.with(mContext).load(R.drawable.default_user).into(mPhotoUrl);
        pathImage = mIntent.getStringExtra("photo_url");
        setListener();

    }

    private void setListener() {
        final ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MultipartBody.Part body = null;
                //dicek apakah image sama dengan yang ada di server atau berubah
                //jika sama dikirim lagi jika berbeda akan dikirim ke server
                if ((!pathImage.contains("upload/" + edtIdUser.getText().toString())) &&
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

                RequestBody reqIdUser=
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdUser.getText().toString().isEmpty())?
                                        "" : edtIdUser.getText().toString());
                RequestBody reqNama = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtNamaUser.getText().toString().isEmpty())?"":edtNamaUser.getText().toString());
                RequestBody reqUmur = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtUmur.getText().toString().isEmpty())?"":edtUmur.getText().toString());
                RequestBody reqJenis = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtJenis.getText().toString().isEmpty())?"":edtJenis.getText().toString());
                RequestBody reqWarna = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtWarna.getText().toString().isEmpty())?"":edtWarna.getText().toString());
                RequestBody reqUsername = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtUsername.getText().toString().isEmpty())?"":edtUsername.getText().toString());
                RequestBody reqPassword = MultipartBody.create(MediaType.parse("multipart/form-data"),
                        (edtPassword.getText().toString().isEmpty())?"":edtPassword.getText().toString());

                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"), "update");

                Call<GetUser> callUpdate = mApiInterface.putUser(body, reqIdUser,reqNama, reqUmur, reqJenis,
                        reqWarna, reqUsername, reqPassword, reqAction );

                callUpdate.enqueue(new Callback<GetUser>() {
                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        //Log.d("Update Retrofit ", response.body().getStatus());
                        if (response.body().getStatus().equals("failed")){
                            tvMessage.setText("Retrofit Update \n Status = " + response.body()
                                    .getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+"\n");
                        }else{
                            String detail = "\n"+
                                    "id_pembeli = "+response.body().getResult().get(0).getId_user()+"\n"+
                                    "nama = "+response.body().getResult().get(0).getNama()+"\n"+
                                    "umur = "+response.body().getResult().get(0).getUmur()+"\n"+
                                    "jenisKulit = "+response.body().getResult().get(0).getJenis_kulit()+"\n"+
                                    "warnaKulit = "+response.body().getResult().get(0).getWarna_kulit()+"\n"+
                                    "username = "+response.body().getResult().get(0).getUsername()+"\n"+
                                    "password = "+response.body().getResult().get(0).getPassword()+"\n"+
                                    "photo_url_user = "+response.body().getResult().get(0).getPhotoUrlUser()
                                    +"\n";
                            tvMessage.setText("Retrofit Update \n Status = "+response.body().getStatus()+"\n"+
                                    "Message = "+response.body().getMessage()+detail);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        //Log.d("Update Retrofit ", t.getMessage());
                        tvMessage.setText("Retrofit Update \n Status = "+ t.getMessage());
                    }
                });

            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody reqIdUser =
                        MultipartBody.create(MediaType.parse("multipart/form-data"),
                                (edtIdUser.getText().toString().isEmpty())?
                                        "" : edtIdUser.getText().toString());
                RequestBody reqAction =
                        MultipartBody.create(MediaType.parse("multipart/form-data"), "delete");

                Call<GetUser> callDelete = mApiInterface.deleteUser(reqIdUser,reqAction);
                callDelete.enqueue(new Callback<GetUser>() {
                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        tvMessage.setText("Retrofit Delete \n Status = "+response.body()
                                .getStatus() +"\n"+
                                "Message = "+response.body().getMessage()+"\n");
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        tvMessage.setText("Retrofit Delete \n Status = "+ t.getMessage());
                    }
                });
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tempIntent = new Intent(mContext, ListUser.class);
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
