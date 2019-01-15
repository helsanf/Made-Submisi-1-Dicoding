package com.example.helsanf.catalogemovie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.model.MovieItem;
import com.example.helsanf.catalogemovie.res.Api;
import com.example.helsanf.catalogemovie.res.ApiInterface;
import com.example.helsanf.catalogemovie.res.SharedManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovie extends AppCompatActivity {
    @BindView(R.id.tv_detail_judul)
    TextView tvJudul;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_desc_detail)
    TextView tvDesc;
    @BindView(R.id.img_detail)
    ImageView imgView;
    SharedManager sharedManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportActionBar().setHomeButtonEnabled(true);
        ButterKnife.bind(this);
        getDetail();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void getDetail(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        sharedManager = new SharedManager(getApplicationContext());
        HashMap<String,String> user = sharedManager.getDetailsMovie();
        int id = Integer.parseInt(user.get(sharedManager.ID_MOVIE));

        Call<MovieItem> call = apiInterface.getDetail(id,ApiInterface.API_KEY,ApiInterface.BAHASA);
        call.enqueue(new Callback<MovieItem>() {
            @Override
            public void onResponse(Call<MovieItem> call, Response<MovieItem> response) {
                tvJudul.setText("Title : "+response.body().getTitle());
//                tvRating.setText((int) response.body().getVoteAverage());
                tvRating.setText("Rating In IMDB : "+String.valueOf(response.body().getVoteAverage()));
                tvDesc.setText("Descripsi : "+response.body().getDeskripsi());
                Log.d("onRespone ","Title "+String.valueOf(response.body().getTitle()));
                String movie = response.body().getPosterPath();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.ic_launcher);
                requestOptions.error(R.mipmap.ic_launcher);
                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load("http://image.tmdb.org/t/p/w154"+movie)
                        .into(imgView);
            }

            @Override
            public void onFailure(Call<MovieItem> call, Throwable t) {
                Log.e("OnFailure ","Gagal "+String.valueOf(t.getMessage()));
            }
        });
    }
}
