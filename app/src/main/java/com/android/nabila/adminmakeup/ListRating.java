package com.android.nabila.adminmakeup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.nabila.adminmakeup.Adapter.RatingAdapter;
import com.android.nabila.adminmakeup.Model.GetRating;
import com.android.nabila.adminmakeup.Model.PostDelRating;
import com.android.nabila.adminmakeup.Model.Rating;
import com.android.nabila.adminmakeup.Rest.ApiClient;
import com.android.nabila.adminmakeup.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRating extends AppCompatActivity {
    Button btGet, btUpdate, btInsert, btDelete;
    ApiInterface mApiInterface;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rating);
        btGet = (Button) findViewById(R.id.btGet);
        btInsert = (Button) findViewById(R.id.btInsert);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mApiInterface  = ApiClient.getClient().create(ApiInterface.class);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GetRating> ratingCall = mApiInterface.getRating();
                ratingCall.enqueue(new Callback<GetRating>() {
                    @Override
                    public void onResponse(Call<GetRating> call, Response<GetRating> response) {
                        List<Rating> ratingList = response.body().getListDataRating();
                        Log.d("Retrofit Get", "Jumlah data rating: " + String.valueOf(ratingList.size()));
                        mAdapter = new RatingAdapter(ratingList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetRating> call, Throwable t) {
                        // Log error
                        Log.e("Retrofit Get", t.toString());
                    }
                });
            }
        });

    }
}
