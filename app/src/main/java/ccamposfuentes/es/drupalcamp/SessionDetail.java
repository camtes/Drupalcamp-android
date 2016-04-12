package ccamposfuentes.es.drupalcamp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.adapters.SpeakerAdapter;
import ccamposfuentes.es.drupalcamp.adapters.SpeakersSessionAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;
import ccamposfuentes.es.drupalcamp.utils.Utils;

public class SessionDetail extends AppCompatActivity {

    private Session session;
    private ImageButton ibStart1, ibStart2, ibStart3, ibStart4, ibStart5;
    private TextView title, sumary;
    private RecyclerView speakers;
    private List<Speaker> speakersItems;
    DBHelper mDBHelper;

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

        speakers = (RecyclerView) findViewById(R.id.rv_sessions_detail_speakers);
        assert speakers != null;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        speakers.setHasFixedSize(true);
        speakers.setHasFixedSize(true);
        speakers.setLayoutManager(mLayoutManager);

        ibStart1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
            }
        });

        ibStart2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
            }
        });

        ibStart3.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.gray);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
            }
        });

        ibStart4.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Utils.tintImageButton(getApplicationContext(), ibStart1, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart2, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart3, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart4, R.color.colorAccent);
                Utils.tintImageButton(getApplicationContext(), ibStart5, R.color.gray);
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
            }
        });

        // Get session data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            loadSession(bundle.getString("idSession"));
        }

    }

    public void loadSession(String id) {
        // Connect to database
        DBHelper mDBHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);

        Dao dao;

        try {
            dao = mDBHelper.getSessionDao();
            session = (Session) dao.queryForId(id);
            if (session != null) {

                // Connect to database
                mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);
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

                SpeakersSessionAdapter mAdapter = new SpeakersSessionAdapter(speakersItems, this);
                speakers.setAdapter(mAdapter);

                setSession();
            }
            Log.i("SessionDetail", "Load session.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set session UI data
     */
    public void setSession() {
        title.setText(session.getTitle());
        sumary.setText(Html.fromHtml(session.getText()));
    }
}
