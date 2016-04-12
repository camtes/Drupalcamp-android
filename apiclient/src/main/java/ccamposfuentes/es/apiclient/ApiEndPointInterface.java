package ccamposfuentes.es.apiclient;

import org.json.JSONObject;

import java.util.List;

import ccamposfuentes.es.apiclient.restObject.RestDeviceId;
import ccamposfuentes.es.apiclient.restObject.RestRegister;
import ccamposfuentes.es.apiclient.restObject.RestSession;
import ccamposfuentes.es.apiclient.restObject.RestSpeaker;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 09/04/16
 * Project: DrupalCamp
 */

public interface ApiEndPointInterface {

    String masterToken = "Dhkc_C7RNrSQfJTmUrEYKXa1Ol2gT4aTpMeR2pXrF2w";

    // Register
    @Headers({
            "Authorization: Bearer "+masterToken,
            "Content-Type: application/json"
    })
    @POST("user_for_app")
    Call<RestRegister> getToken(@Body JSONObject deviceId);

    // Get Sessions
    @Headers({
            "Content-Type: application/json"
    })
    @GET("sessions")
    Call<List<RestSession>> getSessions(@Header("Authorization") String token);


    // Get speakers
    @Headers({
            "Content-Type: application/json"
    })
    @GET("speakers")
    Call<List<RestSpeaker>> getSpeakers(@Header("Authorization") String token);
}
