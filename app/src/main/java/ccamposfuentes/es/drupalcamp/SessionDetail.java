package ccamposfuentes.es.drupalcamp;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.apiclient.ApiClient;
import ccamposfuentes.es.apiclient.ApiEndPointInterface;
import ccamposfuentes.es.apiclient.restObject.RestSession;
import ccamposfuentes.es.apiclient.restObject.RestValuation;
import ccamposfuentes.es.apiclient.restObject.RestVote;
import ccamposfuentes.es.drupalcamp.adapters.SpeakersSessionAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;
import ccamposfuentes.es.drupalcamp.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionDetail extends AppCompatActivity {

    private Session session;
    private ImageButton ibStart1, ibStart2, ibStart3, ibStart4, ibStart5;
    private TextView title, sumary;
    private RecyclerView rvSpeakers;
    private List<Speaker> speakersItems;
    private int rate;
    DBHelper mDBHelper;
    private RelativeLayout loadLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);

        if (getSupportActionBar() != null)
            getSupportActionBar().setElevation(0);

        speakersItems = new ArrayList<>();

        title = (TextView) findViewById(R.id.tv_session_detail_title);
        sumary = (TextView) findViewById(R.id.tv_session_detail_sumary);
        ibStart1 = (ImageButton) findViewById(R.id.ib_start_1);
        ibStart2 = (ImageButton) findViewById(R.id.ib_start_2);
        ibStart3 = (ImageButton) findViewById(R.id.ib_start_3);
        ibStart4 = (ImageButton) findViewById(R.id.ib_start_4);
        ibStart5 = (ImageButton) findViewById(R.id.ib_start_5);
        progressBar = (ProgressBar) findViewById(R.id.session_detail_progressbar);
        loadLayout = (RelativeLayout) findViewById(R.id.session_detail_loadpanel);

        rvSpeakers = (RecyclerView) findViewById(R.id.rv_sessions_detail_speakers);
        assert rvSpeakers != null;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvSpeakers.setHasFixedSize(true);
        rvSpeakers.setHasFixedSize(true);
        rvSpeakers.setLayoutManager(mLayoutManager);

        // Get session data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loadSession(bundle.getString("idSession"));
        }
    }

    public void loadSession(String id) {
//        // Connect to database
//        DBHelper mDBHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
//
//        Dao dao;
//
//        try {
//            dao = mDBHelper.getSessionDao();
//            session = (Session) dao.queryForId(id);
//            if (session != null) {
//
//                // Connect to database
//                mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);
//                Speaker speaker;
//                for (String s : session.getSpeakers()) {
//                    Dao<Speaker, Integer> daoSpeaker;
//                    try {
//                        daoSpeaker = mDBHelper.getSpeakerDao();
//                        speaker = daoSpeaker.queryForId(Integer.valueOf(s));
//                        speakersItems.add(speaker);
//
//                    } catch (SQLException e) {
//                        Log.e("SpeakerActivity", "Error buscando usuario");
//                    }
//                }
//
//                SpeakersSessionAdapter mAdapter = new SpeakersSessionAdapter(speakersItems, this);
//                speakers.setAdapter(mAdapter);
//
//                setSession();
//            }
//            Log.i("SessionDetail", "Load session.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

            // Retrofit - Call
            ApiEndPointInterface client = ApiClient.createService(ApiEndPointInterface.class);
            final Call<RestSession> call = client.getSession("Bearer "
                    + Utils.readSharedPrefences(this, getString(R.string.lToken)), id);

            call.enqueue(new Callback<RestSession>() {
                @Override
                public void onResponse(Call<RestSession> call, Response<RestSession> response) {

                    RestSession item = (RestSession) response.body();

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

                session = new Session(
                        nid, title, text, difficulty, language, date,room,speakers, type
                );


                mDBHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);
                Speaker speaker;
                for (String s : session.getSpeakers()) {
                    Dao<Speaker, Integer> daoSpeaker;
                    try {
                        daoSpeaker = mDBHelper.getSpeakerDao();
                        speaker = daoSpeaker.queryForId(Integer.valueOf(s));
                        speakersItems.add(speaker);

                    } catch (SQLException e) {
                        Log.e("SpeakerActivity", "Error buscando usuario");
                    }
                }

                SpeakersSessionAdapter mAdapter = new SpeakersSessionAdapter(speakersItems, getApplicationContext());
                rvSpeakers.setAdapter(mAdapter);

                setSession();


                }

                @Override
                public void onFailure(Call<RestSession> call, Throwable t) {

                    // TODO manage get tourist spots fail
                    Log.e("INTRO", "Fail sessions load");
                }
            });
    }

    /**
     * Set session UI data
     */
    public void setSession() {
        loadLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        rate = Utils.getIntSharedPrefences(getApplicationContext(), session.getId());
        if (rate != 0) {

            switch (rate) {
                case 1:
                    Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.gray);
                    Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.gray);
                    Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                    Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);

                    break;
                case 2:
                    Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.gray);
                    Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                    Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);

                    break;

                case 3:
                    Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                    Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);

                    break;

                case 4:
                    Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);

                    break;

                case 5:
                    Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.colorAccent);
                    Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.colorAccent);

                    break;
            }
        }

        ibStart1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
                setValuation(1);
            }
        });

        ibStart2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
                setValuation(2);
            }
        });

        ibStart3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
                setValuation(3);
            }
        });

        ibStart4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
                setValuation(4);
            }
        });

        ibStart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.colorAccent);
                setValuation(5);
            }
        });

        title.setText(session.getTitle());
        sumary.setText(Html.fromHtml(session.getText()));
    }

    /**
     * Set rate session
     * @param points point for session (1-5)
     */
    public void setValuation(int points) {

        Utils.saveIntSharedPreferences(getApplicationContext(), session.getId(), points);

        ApiEndPointInterface client = ApiClient.createService(ApiEndPointInterface.class);
        String token = Utils.readSharedPrefences(this, getString(R.string.lToken));
        String authToken = "Bearer "+token;
        RestValuation restValuation = new RestValuation(points, session.getId(), token);

        String name = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (rate == 0) {
            // POST
            rate = points;

            RestVote vote = new RestVote(name,
                    points, Integer.valueOf(session.getId()), Integer.valueOf(Utils.readSharedPrefences(this, getString(R.string.mUid))));

            final Call<ResponseBody> call = client.setValuation(authToken, "json", vote);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("SessionDetail", response.raw().toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("err", t.toString());
                }
            });
        }
        else {
            // PATCH

//            RestPatchVote vote = new RestPatchVote(name, points);
//
//            final Call<ResponseBody> call = client.setPatchValuation(authToken, "json", vote);
//
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    Log.i("SessionDetail", response.raw().toString());
//
//                    if (response.raw().code() == 201)
//                        Toast.makeText(SessionDetail.this, "Puntuación enviada correctamente", Toast.LENGTH_SHORT).show();
//                    else if(response.raw().code() == 404)
//                        Toast.makeText(SessionDetail.this, "Not found", Toast.LENGTH_SHORT).show();
//                    else if (response.raw().code() == 500)
//                        Toast.makeText(SessionDetail.this, "Error 500", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Log.e("err", t.toString());
//                }
//            });
        }



    }
}
