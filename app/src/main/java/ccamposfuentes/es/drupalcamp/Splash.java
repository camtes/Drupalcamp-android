package ccamposfuentes.es.drupalcamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.apiclient.ApiClient;
import ccamposfuentes.es.apiclient.ApiEndPointInterface;
import ccamposfuentes.es.apiclient.restObject.RestSession;
import ccamposfuentes.es.apiclient.restObject.RestSpeaker;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 12/04/16
 * Project: DrupalCamp
 */

public class Splash extends AppCompatActivity {

    List<Session> itemsSessions;
    List<Speaker> itemsSpeakers;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemsSessions = new ArrayList<>();

        // Connect to database
        mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        // Save sessions if db is empty
        SharedPreferences sharedPreferences = this.getSharedPreferences("DrupalCamp",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("database", false)) {
            editor.putBoolean("database", true);
            editor.apply();

            getSessions();
            getSpeaker();
        }

        if (!sharedPreferences.getBoolean("register", false)) {
            editor.putBoolean("register", true);
            editor.apply();

            user_for_app(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        }

        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


    /**
     * ApiClient
     * Get Sessions
     */
    public void getSessions () {
        // Retrofit - Call
        ApiEndPointInterface client = ApiClient.createService(ApiEndPointInterface.class);
        final Call<List<RestSession>> call = client.getSessions();

        call.enqueue(new Callback<List<RestSession>>() {
            @Override
            public void onResponse(Call<List<RestSession>> call, Response<List<RestSession>> response) {

                ArrayList<RestSession> items = new ArrayList<>(response.body());

                for (RestSession item : items) {
                    String nid = null;
                    String title = null;
                    String text = null;
                    String difficulty = null;
                    String language = null;
                    String date = null;
                    String room = "2";
                    String type = "session";
                    String[] speakers = new String[item.getSpeakers().size()];

                    if (item.getNid().size() > 0)
                        nid = item.getNid().get(0).getValue();

                    if (item.getTitle().size() > 0)
                        title = item.getTitle().get(0).getValue();

                    if (item.getText().size() > 0)
                        text = item.getText().get(0).getValue();

                    if (item.getDifficulty().size() > 0)
                        if (item.getDifficulty().size() > 0)
                            difficulty = item.getDifficulty().get(0).getTarget_id();

                    if (item.getLanguage().size() > 0)
                        language = item.getLanguage().get(0).getValue();

                    if (item.getDate().size() > 0)
                        date = item.getDate().get(0).getValue();

                    if (item.getRoom().size() > 0)
                        room = item.getRoom().get(0).getValue();

                    if (item.getType() != null)
                        if (item.getType().size() > 0)
                            type = item.getType().get(0).getValue();
                        else
                            type = "session";

                    if (item.getSpeakers().size() > 0)
                        for (int i=0;i<item.getSpeakers().size();i++) {
                            speakers[i] = item.getSpeakers().get(i).getTarget_id();
                        }

                    itemsSessions.add(new Session(
                            nid, title, text, difficulty, language, date,room,speakers, type
                    ));
                }

                for (Session s : itemsSessions) {

                    Dao<Session, Integer> dao;
                    try {
                        dao = mDBHelper.getSessionDao();
                        dao.create(s);
                    } catch (SQLException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RestSession>> call, Throwable t) {

                // TODO manage get tourist spots fail
                Log.e("INTRO", "Fail sessions load");
            }

        });
    }

    /**
     * ApiClient
     * Get Speakers
     */
    public void getSpeaker () {
        // Retrofit - Call
        ApiEndPointInterface client = ApiClient.createService(ApiEndPointInterface.class);
        final Call<List<RestSpeaker>> call = client.getSpeakers();

        call.enqueue(new Callback<List<RestSpeaker>>() {
            @Override
            public void onResponse(Call<List<RestSpeaker>> call, Response<List<RestSpeaker>> response) {

                ArrayList<RestSpeaker> items = new ArrayList<>(response.body());
                itemsSpeakers = new ArrayList<>();

                for (RestSpeaker item : items) {
                    String uid = null;
                    String username = null;
                    String name = null;
                    String company = null;
                    String picture = null;

                    if (item.getUid().size() > 0)
                        uid = item.getUid().get(0).getValue();

                    if (item.getUsername().size() > 0)
                        username = item.getUsername().get(0).getValue();

                    if (item.getName().size() > 0)
                        name = item.getName().get(0).getValue();

                    if (item.getCompany().size() > 0)
                        company = item.getCompany().get(0).getValue();

                    if (item.getPicture().size() > 0)
                        picture = item.getPicture().get(0).getUrl();


                    if (picture != null)
                        itemsSpeakers.add(new Speaker(uid, username, name, company, picture));
                    else
                        itemsSpeakers.add(new Speaker(uid, username, name, company));
                }

                for (Speaker s : itemsSpeakers) {

                    Dao<Speaker, Integer> dao;
                    try {
                        dao = mDBHelper.getSpeakerDao();
                        dao.create(s);
                    } catch (SQLException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RestSpeaker>> call, Throwable t) {

                // TODO manage get tourist spots fail
                Log.e("INTRO", "Fail sessions load");
            }
        });
    }

    /**
     * Apicliente
     * Register new device
     * @param deviceId deviceId
     */
    public void user_for_app(String deviceId) {
        // Retrofit - Call
        ApiEndPointInterface client = ApiClient.createService(ApiEndPointInterface.class);
        final Call<List<RestSpeaker>> call = client.getSpeakers();

        call.enqueue(new Callback<List<RestSpeaker>>() {

            @Override
            public void onResponse(Call<List<RestSpeaker>> call, Response<List<RestSpeaker>> response) {

            }

            @Override
            public void onFailure(Call<List<RestSpeaker>> call, Throwable t) {

            }
        });
    }
}