package com.loganspears.movies.networking;

import android.content.Context;

import com.loganspears.movies.R;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by logan on 9/20/16.
 */
public class TheMovieDbClient {
    private static Retrofit retrofit;

    public static synchronized Retrofit getClient(final Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    String apiKey = context.getString(R.string.themoviedb_api_key);
                    // found on http://stackoverflow.com/questions/32948083/is-there-a-way-to-add-query-parameter-to-every-request-with-retrofit-2
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .addQueryParameter("api_key",apiKey)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            }).build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://api.themoviedb.org/3/movie/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
