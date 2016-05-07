package com.abcxo.android.ifootball.restfuls;

import android.support.annotation.NonNull;

import com.abcxo.android.ifootball.constants.Constants;
import com.abcxo.android.ifootball.models.Game;
import com.abcxo.android.ifootball.restfuls.interceptor.DecryptedPayloadInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by shadow on 15/11/1.
 */
public class GameRestful {
    public static GameRestful INSTANCE = new GameRestful();
    private GameService gameService;

    public interface GameService {
        @GET(Constants.PATH + "/game/list")
        Call<List<Game>> gets(@Query("uid") long uid,
                              @Query("pageIndex") int pageIndex,
                              @Query("pageSize") int pageSize);

    }


    private GameRestful() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new DecryptedPayloadInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gameService = retrofit.create(GameService.class);
    }


    public interface OnGameRestfulList {
        void onSuccess(List<Game> games);

        void onError(RestfulError error);

        void onFinish();

    }


    //获取直播列表
    public void gets(long uid, int pageIndex, @NonNull final OnGameRestfulList onList) {
        Call<List<Game>> call = gameService.gets(uid, pageIndex, Constants.PAGE_SIZE);
        call.enqueue(new OnRestful<List<Game>>() {
            @Override
            void onSuccess(List<Game> games) {
                onList.onSuccess(games);
            }

            @Override
            void onError(RestfulError error) {
                onList.onError(error);
            }

            @Override
            void onFinish() {
                onList.onFinish();
            }
        });

    }


}
