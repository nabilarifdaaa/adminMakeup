package com.android.nabila.adminmakeup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nabila.adminmakeup.Model.GetRating;
import com.android.nabila.adminmakeup.Model.PostDelRating;
import com.android.nabila.adminmakeup.Rest.ApiClient;
import com.android.nabila.adminmakeup.Rest.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarInsertRating extends AppCompatActivity {
    EditText edtIdPro, edtIdUser, edtRating, edtReview, edtTgl;
    Context mContext;
    ImageView mImageView;
    Button btAddPhotoId, btBack, btInsert, btDelete;
    TextView tvMessage;
    String imagePath = "";
    ApiInterface mApiInterface;
    String idRating = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_insert_rating);
        edtIdPro = (EditText) findViewById(R.id.edtIdProduk);
        edtIdUser = (EditText) findViewById(R.id.edtIdUser);
        edtRating = (EditText) findViewById(R.id.edtRating);
        edtReview = (EditText) findViewById(R.id.edtReview);
        edtTgl = (EditText) findViewById(R.id.edtTanggal);
        tvMessage = (TextView) findViewById(R.id.tvMessage2);


        Intent mIntent = getIntent();
        idRating = mIntent.getStringExtra("idRating");
        edtIdPro.setText(mIntent.getStringExtra("idProduk"));
        edtIdUser.setText(mIntent.getStringExtra("idUser"));
        edtRating.setText(mIntent.getStringExtra("rating"));
        edtReview.setText(mIntent.getStringExtra("review"));
        edtTgl.setText(mIntent.getStringExtra("tanggal"));

        btInsert = (Button) findViewById(R.id.btInsert2);
        btDelete = (Button) findViewById(R.id.btDelete2);
        btBack = (Button) findViewById(R.id.btBack);


        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GetRating> postPembelianCall = mApiInterface.postRating(
                        edtIdPro.getText().toString(),
                        edtIdUser.getText().toString(),
                        edtRating.getText().toString(),
                        edtReview.getText().toString(),
                        edtTgl.getText().toString());

                postPembelianCall.enqueue(new Callback<GetRating>() {
                    @Override
                    public void onResponse(Call<GetRating> call, Response<GetRating> response) {
                        tvMessage.setText(" Retrofit Insert: " +
                                "\n " + " Status Insert : " +
                                response.body().getStatus() +
                                "\n " + " Message Insert : "+ response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<GetRating> call, Throwable t) {
                        tvMessage.setText("Retrofit Insert: \n Status Insert :"+ t.getMessage());
                    }
                });
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!idRating.trim().isEmpty()){

                    Call<PostDelRating> deletePembelian = mApiInterface.deleteRating(idRating);
                    deletePembelian.enqueue(new Callback<PostDelRating>() {
                        @Override
                        public void onResponse(Call<PostDelRating> call, Response<PostDelRating> response) {
                            tvMessage.setText(" Retrofit Delete: " +
                                    "\n " + " Status Delete : " +response.body().getStatus() +
                                    "\n " + " Message Delete : "+ response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<PostDelRating> call, Throwable t) {
                            tvMessage.setText("Retrofit Delete: \n Status Delete :"+ t.getMessage());
                        }
                    });
                }else{
                    tvMessage.setText("id_pembelian harus diisi");
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), ListRating.class);
                startActivity(mIntent);
            }
        });

    }


}
