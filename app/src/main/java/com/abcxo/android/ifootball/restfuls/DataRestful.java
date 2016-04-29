package com.abcxo.android.ifootball.restfuls;

import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Data;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class DataRestful {
    public static DataRestful INSTANCE = new DataRestful();
    private DataService dataService;

    public interface DataService {
        @GET(Constants.PATH + "/data")
        Call<Data> get(@Query("uid") long uid, @Query("name") String name, @Query("category") String category);
    }


    private DataRestful() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataService = retrofit.create(DataService.class);
    }


    public interface OnDataRestfulGet {
        void onSuccess(Data data);

        void onError(RestfulError error);

        void onFinish();

    }


    //获取直播列表
    public void get(long uid, String name, String category, @NonNull final OnDataRestfulGet onGet) {
        Call<Data> call = dataService.get(uid, name, category);
        call.enqueue(new OnRestful<Data>() {
            @Override
            void onSuccess(Data data) {
                onGet.onSuccess(data);
            }

            @Override
            void onError(RestfulError error) {
                onGet.onError(error);
            }

            @Override
            void onFinish() {
                onGet.onFinish();
            }
        });

    }


}
