package com.example.helsanf.catalogemovie.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helsanf.catalogemovie.res.ItemClickSupport;
import com.example.helsanf.catalogemovie.R;
import com.example.helsanf.catalogemovie.adapter.AdapterMovie;
import com.example.helsanf.catalogemovie.getModel.getMovie;
import com.example.helsanf.catalogemovie.model.MovieItem;
import com.example.helsanf.catalogemovie.res.Api;
import com.example.helsanf.catalogemovie.res.ApiInterface;
import com.example.helsanf.catalogemovie.res.SharedManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<MovieItem> listMovie;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterMovie adapterMovie;
    Context context;
    SharedManager sharedManager;
    SweetAlertDialog sweetAlertDialog;

    @BindView(R.id.et_judul)
    EditText editText;

    @BindView(R.id.tv_data_null)
    TextView mDataNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedManager = new SharedManager(getApplicationContext());
        mRecyclerView = findViewById(R.id.list_movie);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
       adapterMovie = new AdapterMovie(this);

    }
    @OnClick(R.id.btn_cari)
    public void cariData(){
        String judul = editText.getText().toString();
        getMovie(judul);
    }

    private void getMovie(String judul){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<getMovie> call = apiInterface.getMovie(ApiInterface.API_KEY,ApiInterface.BAHASA,judul);
        call.enqueue(new Callback<getMovie>() {
            @Override
            public void onResponse(Call<getMovie> call, Response<getMovie> response) {
                final List<MovieItem> movieItems = response.body().getResults();
                Log.d("TEST RESULT ","onRespone "+String.valueOf(movieItems.size()));
                if(movieItems.size()==0){
//                    Toast.makeText(MainActivity.this, "Data Tidak Ada , Silahkan Periksa Keywoard Pencarian Anda",
//                            Toast.LENGTH_LONG).show();
                    sweetAlertDialog = new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE);
                    sweetAlertDialog.getProgressHelper().setSpinSpeed(100);
                    sweetAlertDialog.setTitleText("WARNING");
                    sweetAlertDialog.setContentText("DATA TIDAK DITEMUKAN");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            movieItems.clear();
                            mRecyclerView.setAdapter(null); //clear jika data tidak ditemukan
                        }
                    });
                    sweetAlertDialog.show();
                }else{
                    adapterMovie.setListMovie(movieItems);
                    reloadView(adapterMovie,movieItems);
                }

            }

            @Override
            public void onFailure(Call<getMovie> call, Throwable t) {
                Log.e("onFailure ","Gagal "+String.valueOf(t.getMessage()));
            }
        });
    }

    private void clickItemDetail(MovieItem movieItem){
        Intent detailActivity = new Intent(this, DetailMovie.class);
        startActivity(detailActivity);
        this.overridePendingTransition(0,0);
    }

    public void reloadView(RecyclerView.Adapter adapter , final List<MovieItem> movieItems){
        mRecyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MovieItem mList = movieItems.get(position);
                int id_movie = mList.getId();
                sharedManager.createIdMovie(String.valueOf(id_movie));
                clickItemDetail(movieItems.get(position));
            }
        });
    }


}
