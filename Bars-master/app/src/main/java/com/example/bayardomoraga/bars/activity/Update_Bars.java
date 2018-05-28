package com.example.bayardomoraga.bars.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.bayardomoraga.bars.R;
import com.example.bayardomoraga.bars.api.Api;
import com.example.bayardomoraga.bars.model.BarsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_Bars extends AppCompatActivity {
    private BarsModel bar;
    private EditText name;
    private EditText description;
    private EditText address;
    private EditText type;
    //Recuperar Datos
    private String barId;
    private String barName;
    private String barDescription;
    private String barAddress;
    private  String barType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getExtras();
        initializeViews();
    }
    public void initializeViews (){
        name = findViewById(R.id.etname);
        description = findViewById(R.id.etdescription);
        address = findViewById(R.id.etaddress);
        type= findViewById(R.id.ettype);

        name.setText(barName);
        description.setText(barDescription);
        address.setText(barAddress);
        type.setText(barType);
    }
    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            barId = extras.getString("Id");
            barName = extras.getString("Name");
            barDescription = extras.getString("Description");
            barAddress = extras.getString("Address");
            barType=extras.getString("Type");
        }
    }
    public void updateBars() {
        bar = new BarsModel();

        bar.setName(name.getText().toString());
        bar.setDescription(description.getText().toString());
        bar.setAddress(address.getText().toString());
        bar.setType(type.getText().toString());

        Call<BarsModel> call = Api.instance().updatebars(barId, bar);
        call.enqueue(new Callback<BarsModel>() {
            @Override
            public void onResponse(@NonNull Call<BarsModel> call, @NonNull Response<BarsModel> response) {

            }

            @Override
            public void onFailure(@NonNull Call<BarsModel> call, @NonNull Throwable t) {
                Log.i("Debug: ", t.getMessage());
            }
        });
    }

    public void acceptOnclick(View view) {
        updateBars();
        finish();

    }

    public void cancelOnclick(View view) {
        finish();
    }

}
