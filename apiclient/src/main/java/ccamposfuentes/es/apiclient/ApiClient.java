package ccamposfuentes.es.apiclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ccamposfuentes.es.apiclient.restObject.RestSession;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 09/04/16
 * Project: DrupalCamp
 */

public class ApiClient {
    public static final String BASE_URL = "http://rest-dcamp.front.id/";

    public static <S> S createService(Class<S> serviceClass){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getDefaultClient())
                .build();

        return retrofit.create(serviceClass);
    }

    /**
     * Init default HTTP client to connect with server
     * @return OkHttpClient
     */
    private static OkHttpClient getDefaultClient () {

        return new OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

    }
}
