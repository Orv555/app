package com.example.bayardomoraga.bars.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bayardomoraga.bars.R;
import com.example.bayardomoraga.bars.api.Api;
import com.example.bayardomoraga.bars.model.BarsModel;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Delete_Bars extends AppCompatActivity {
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Context context = this;
        getExtras();
        deleteProduct(context);
        deleteProducts();
        finish();
    }
    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("Id");
        }
    }
    private void deleteProduct(final Context context){
        Call<BarsModel> call = Api.instance().deletebars(id);
        call.enqueue(new Callback<BarsModel>() {
            @Override
            public void onResponse(Call<BarsModel> call, Response<BarsModel> response) {
                if(response.body() != null){
                    Toast.makeText(context, "Elemento Borrado", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BarsModel> call, Throwable t) {
                Log.i("Debug: ", t.getMessage());

            }
        });
    }

    //delete DATABASE
    private void deleteProducts(){
        io.realm.Realm realm = Realm.getDefaultInstance();

        RealmQuery<BarsModel> query = realm.where(BarsModel.class);

        RealmResults<BarsModel> results = query.findAll();

        for (int i =0; i<results.size(); i++)
        {
            if (id.equals(results.get(i).getId()))
            {
                deleteProduct(results.get(i));
            }
        }
    }
    private void deleteProduct(BarsModel productModel){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        productModel.deleteFromRealm();
        realm.commitTransaction();
    }
}
