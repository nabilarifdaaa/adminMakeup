package com.android.nabila.adminmakeup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.nabila.adminmakeup.Adapter.UserAdapter;
import com.android.nabila.adminmakeup.Model.GetUser;
import com.android.nabila.adminmakeup.Model.User;
import com.android.nabila.adminmakeup.Rest.ApiClient;
import com.android.nabila.adminmakeup.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUser extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    ApiInterface mApiInterface;
    Button btGet,btAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        mContext = getApplicationContext();
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        btGet =  findViewById(R.id.btGet);
        btAddData =findViewById(R.id.btInsert);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetUser> mPembeliCall = mApiInterface.getUser();
                mPembeliCall.enqueue(new Callback<GetUser>() {

                    @Override
                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                        Log.d("Get User", response.body().getStatus());
                        List<User> listUser = response.body().getResult();
                        mAdapter = new UserAdapter(listUser);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetUser> call, Throwable t) {
                        Log.d("Get User", t.getMessage());
                    }
                });
            }
        });

        btAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LayarInsertUser.class);
                startActivity(intent);
            }
        });



    }
}
