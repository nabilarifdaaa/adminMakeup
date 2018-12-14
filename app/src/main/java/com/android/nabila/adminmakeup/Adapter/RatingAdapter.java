package com.android.nabila.adminmakeup.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.nabila.adminmakeup.LayarInsertRating;
import com.android.nabila.adminmakeup.Model.Produk;
import com.android.nabila.adminmakeup.Model.Rating;
import com.android.nabila.adminmakeup.R;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingViewHolder>{
    List<Rating> listRating;

    public RatingAdapter(List<Rating> mlistRating) {
        listRating = mlistRating;
    }

    @NonNull
    @Override
    public RatingAdapter.RatingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating,parent,false);
        RatingViewHolder mViewHolder = new RatingViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.RatingViewHolder  holder, final int position) {
        holder.tvTanggalRating.setText("Tanggal :  " + listRating.get(position).getTanggal());
        holder.tvIdRating.setText("Id Rating :  " + listRating.get(position).getIdRating());
        holder.tvIdProduk.setText("Id Produk :  " + listRating.get(position).getIdProduk());
        holder.tvIdUser.setText("Id User :  " + listRating.get(position).getIdUser());
        holder.tvRating.setText("Rating :  " + listRating.get(position).getRating());
        holder.tvReview.setText("Review :  " + listRating.get(position).getReview());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), LayarInsertRating.class);
                mIntent.putExtra("idRating",listRating.get(position).getIdRating());
                mIntent.putExtra("idProduk",listRating.get(position).getIdProduk());
                mIntent.putExtra("idUser",listRating.get(position).getIdUser());
                mIntent.putExtra("rating",listRating.get(position).getRating());
                mIntent.putExtra("review",String.valueOf(listRating.get(position).getReview()));
                mIntent.putExtra("tanggal",String.valueOf(listRating.get(position).getTanggal()));
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRating.size();
    }

    public class RatingViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTanggalRating, tvIdRating, tvIdProduk,tvIdUser,tvRating,tvReview;
        public RatingViewHolder(View itemView) {
            super(itemView);
            tvTanggalRating = (TextView) itemView.findViewById(R.id.tvTanggalRating);
            tvIdRating = (TextView) itemView.findViewById(R.id.tvIdRating);
            tvIdProduk = (TextView) itemView.findViewById(R.id.tvIdProduk);
            tvIdUser = (TextView) itemView.findViewById(R.id.tvIdUser);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvReview = (TextView) itemView.findViewById(R.id.tvReview);
        }
    }

}
