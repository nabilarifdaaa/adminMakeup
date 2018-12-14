package com.android.nabila.adminmakeup.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nabila.adminmakeup.LayarEditProduk;
import com.android.nabila.adminmakeup.LayarEditUser;
import com.android.nabila.adminmakeup.Model.Produk;
import com.android.nabila.adminmakeup.Model.User;
import com.android.nabila.adminmakeup.R;
import com.android.nabila.adminmakeup.Rest.ApiClient;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.os.Build.VERSION_CODES.P;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {
    List<Produk> listProduk;
    public ProdukAdapter(List<Produk> listProduk) {
        this.listProduk = listProduk;
    }

    @Override
    public ProdukAdapter.ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false);
        ProdukAdapter.ProdukViewHolder mHolder = new ProdukAdapter.ProdukViewHolder(view);
        return mHolder;

    }

    @Override
    public void onBindViewHolder(ProdukAdapter.ProdukViewHolder holder, final int position) {
        holder.tvIdProduk.setText(listProduk.get(position).getIdProduk());
        holder.tvNama.setText(listProduk.get(position).getNamaProduk());
        holder.tvJenis.setText(listProduk.get(position).getJenisProduk());
        holder.tvHarga.setText(listProduk.get(position).getHargaProduk());
        if (listProduk.get(position).getPhotoUrl() != null ){
            Glide.with(holder.itemView.getContext()).load(ApiClient.BASE_URL + listProduk.get
                    (position).getPhotoUrl())
                    .into(holder.mPhotoURL);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.default_user).into(holder
                    .mPhotoURL);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LayarEditProduk.class);
                intent.putExtra("idProduk",listProduk.get(position).getIdProduk());
                intent.putExtra("namaProduk",listProduk.get(position).getNamaProduk());
                intent.putExtra("jenisProduk",listProduk.get(position).getJenisProduk());
                intent.putExtra("hargaProduk",listProduk.get(position).getHargaProduk());
                intent.putExtra("detailProduk",listProduk.get(position).getDetailProduk());
                intent.putExtra("photo_url",listProduk.get(position).getPhotoUrl());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduk.size();
    }

    public class ProdukViewHolder extends RecyclerView.ViewHolder {
        ImageView mPhotoURL;
        TextView tvIdProduk, tvNama,tvJenis,tvHarga;

        public ProdukViewHolder(View itemView) {
            super(itemView);
            mPhotoURL =  itemView.findViewById(R.id.imgProduk);
            tvIdProduk= itemView.findViewById(R.id.tvIdProduk);
            tvJenis= itemView.findViewById(R.id.tvJenis);
            tvNama =  itemView.findViewById(R.id.tvNama);
            tvHarga= itemView.findViewById(R.id.tvHarga);

        }
    }
}
