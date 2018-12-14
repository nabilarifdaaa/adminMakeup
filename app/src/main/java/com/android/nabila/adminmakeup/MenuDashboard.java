package com.android.nabila.adminmakeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MenuDashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView btUser,btPro,btRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dashboard);
        btUser = findViewById(R.id.btListUser);
        btPro = findViewById(R.id.btListProduk);
        btRating = findViewById(R.id.btListRating);

        btUser.setOnClickListener(this);
        btPro.setOnClickListener(this);
        btRating.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.btListUser: i = new Intent(this,ListUser.class); startActivity(i); break;
            case R.id.btListProduk: i = new Intent(this,ListProduk.class); startActivity(i); break;
            case R.id.btListRating: i = new Intent(this,ListRating.class); startActivity(i); break;
            default: break;
        }
    }
}
