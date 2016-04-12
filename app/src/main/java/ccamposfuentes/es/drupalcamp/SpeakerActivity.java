package ccamposfuentes.es.drupalcamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ccamposfuentes.es.drupalcamp.adapters.SpeakerAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpeakerActivity extends AppCompatActivity {

    private List<Speaker> speakers_;
    private DBHelper mDBHelper;
    private SpeakerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Connect to database
        mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        Dao<Speaker, Integer> dao;
        try {
            dao = mDBHelper.getSpeakerDao();
            speakers_ = dao.queryForAll();

        } catch (SQLException e) {
            Log.e("SpeakerActivity", "Error buscando usuario");
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_speakers);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SpeakerAdapter(speakers_, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
    }
}
