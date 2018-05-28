package com.example.bayardomoraga.bars.api;

import com.example.bayardomoraga.bars.model.BarsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("bars")
    Call<List<BarsModel>> getbars();

    @POST("bars")
    Call<BarsModel> createbars(@Body BarsModel barsModel);

    @PUT("bars/{id}")
    Call<BarsModel> updatebars(@Path("id") String id, @Body BarsModel barsModel);

    @DELETE("bars/{id}")
    Call<BarsModel> deletebars(@Path("id") String id);
}
