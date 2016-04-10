package ccamposfuentes.es.apiclient;

import java.util.List;

import ccamposfuentes.es.apiclient.restObject.RestSession;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 09/04/16
 * Project: DrupalCamp
 */

public interface ApiEndPointInterface {

    String masterToken = "Dhkc_C7RNrSQfJTmUrEYKXa1Ol2gT4aTpMeR2pXrF2w";

    @Headers({
            "Authorization Bearer "+masterToken,
            "Content-Type application/json"
    })
    @GET("user_for_app")
    Call<String> getToken(@Field("deviceId") String id);

    @GET("sessions")
    Call<List<RestSession>> getSessions();

}
