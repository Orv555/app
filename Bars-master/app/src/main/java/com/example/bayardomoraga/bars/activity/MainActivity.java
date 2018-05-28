package com.example.bayardomoraga.bars.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bayardomoraga.bars.R;
import com.example.bayardomoraga.bars.adapter.BarsAdapter;
import com.example.bayardomoraga.bars.api.Api;
import com.example.bayardomoraga.bars.model.BarsModel;
import com.tumblr.remember.Remember;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout srl;
    private RecyclerView recyclerView;

    private RecyclerView.Adapter mAdapter;
    private static final String IS_FIRST_TIME = "is_first_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        srl = findViewById(R.id.actualizar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Create_Bars.class));
                srl.setRefreshing(false);
            }
        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isConnected(MainActivity.this)){
                    getFromDataBase();
                }
                else{
                    getBars();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl.setRefreshing(false);
                    }
                }, 1500);
            }

        });

        initViews();
        configureRecyclerView();

        //Obteniendo Datos
        if(!isConnected(MainActivity.this)){
            getFromDataBase();
        }
        else{
            getBars();
        }

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void getBars()
    {
        Call<List<BarsModel>> call = Api.instance().getbars();
        call.enqueue(new Callback<List<BarsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<BarsModel>> call, Response<List<BarsModel>> response) {
                if (response.isSuccessful()) {
                    BarsAdapter barsAdapter = new BarsAdapter(response.body());
                    recyclerView.setAdapter(barsAdapter);

                    sync(response.body());
                    srl.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BarsModel>> call, @NonNull Throwable t) {
                Log.e("Debug: ", t.getMessage());
            }
        });
    }

    private void sync(List<BarsModel> productModels) {
        for(BarsModel productModel : productModels) {
            store(productModel);
        }
    }

    private void store(BarsModel marketModelFromApi){

        String a=marketModelFromApi.getId();
        if (exist(a)==false) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            BarsModel productModel = realm.createObject(BarsModel.class); // Create a new object

            productModel.setId(marketModelFromApi.getId());
            productModel.setName(marketModelFromApi.getName());
            productModel.setAddress(marketModelFromApi.getAddress());
            productModel.setDescription(marketModelFromApi.getDescription());
            productModel.setType(marketModelFromApi.getType());
            realm.commitTransaction();
        }

    }

    private void getFromDataBase() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<BarsModel> query = realm.where(BarsModel.class);

        RealmResults<BarsModel> results = query.findAll();

        mAdapter = new BarsAdapter(results);
        recyclerView.setAdapter(mAdapter);
    }
    private boolean exist(String id){

        Boolean exist=false;
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<BarsModel> query = realm.where(BarsModel.class);

        RealmResults<BarsModel> results = query.findAll();

        for (int i=0; i<results.size(); i++)
        {
            if (id.equals(results.get(i).getId()))
            {
                exist=true;
            }
        }
        return exist;
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
