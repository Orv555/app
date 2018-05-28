package com.example.bayardomoraga.bars.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bayardomoraga.bars.R;
import com.example.bayardomoraga.bars.api.Api;
import com.example.bayardomoraga.bars.model.BarsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Bars extends AppCompatActivity {
    EditText etname;
    EditText etaddress;
    EditText etdescription;
    EditText ettype;
    Button btnagregar;
    Button btncancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        etname = findViewById(R.id.etname);
        etaddress = findViewById(R.id.etaddress);
        etdescription = findViewById(R.id.etdescription);
        ettype=findViewById(R.id.ettype);
        btnagregar = findViewById(R.id.btnagregar);
        btncancelar = findViewById(R.id.btncancelar);

        btnagregar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                BarsModel bar = new BarsModel();
                bar.setName(etname.getText().toString());
                bar.setDescription(etdescription.getText().toString());
                bar.setAddress(etaddress.getText().toString());
                bar.setType(ettype.getText().toString());
                createBars(bar);
                finish();
            }
        });

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void createBars (BarsModel bar){
        Call<BarsModel> call = Api.instance().createbars(bar);
        call.enqueue(new Callback<BarsModel>() {
            @Override
            public void onResponse(@NonNull Call<BarsModel> call, Response<BarsModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Create_Bars.this,"Bar Creado Exitosamente", Toast.LENGTH_SHORT).show();
                    etaddress.setText("");
                    etname.setText("");
                    etdescription.setText("");
                    ettype.setText("");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BarsModel> call,@NonNull Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}
