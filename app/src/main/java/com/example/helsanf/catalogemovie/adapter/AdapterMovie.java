package com.example.helsanf.catalogemovie.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {
    List<MovieItem> listMovie;
    Context context;
    private String fix;


    public AdapterMovie(Context context) {
        this.context = context;
    }

    public List<MovieItem> getListMovie() {
        return listMovie;
    }

    public void setListMovie(List<MovieItem> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public AdapterMovie.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        AdapterMovie.MyViewHolder myViewHolder = new AdapterMovie.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.mTitle.setText(getListMovie().get(i).getTitle());

        String test = getListMovie().get(i).getDeskripsi();

        if(TextUtils.isEmpty(test)){
            fix = "Data tidak ada";
        }else{
            fix = test.substring(0,15)+"....";
        }
        myViewHolder.mDesc.setText(fix);

        String getDate = getListMovie().get(i).getReleaseDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(getDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String dateFix = newFormat.format(date);
            myViewHolder.mTanggal.setText(dateFix);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.icon_launcher);
        requestOptions.error(R.drawable.icon_launcher);
            Glide.with(context.getApplicationContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load("http://image.tmdb.org/t/p/w500"+getListMovie().get(i).getPosterPath())
                    .into(myViewHolder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTitle;
        @BindView(R.id.tv_desc)
        TextView mDesc;
        @BindView(R.id.tv_tanggal)
        TextView mTanggal;
        @BindView(R.id.image_view)
        ImageView imgMovie;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
