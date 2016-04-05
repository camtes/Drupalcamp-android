package ccamposfuentes.es.drupalcamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.adapters.SpeakerAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objets.Session;
import ccamposfuentes.es.drupalcamp.objets.Speaker;

public class SpeakerActivity extends AppCompatActivity {

    List<Speaker> speakers_;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Connect to database
        mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        Dao dao;
        try {
            dao = mDBHelper.getSpeakerDao();
            List speakers = dao.queryForAll();
            if (speakers.isEmpty()) {
                Log.d("SpeakerActivity", "No se encontraron usuarios con nombre = Fede");
            } else {
                Log.d("SpeakerActivity", "Recuperado usuarios con nombre = Fede " + speakers);
            }

            speakers_ = speakers;
        } catch (SQLException e) {
            Log.e("SpeakerActivity", "Error buscando usuario");
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_speakers);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        SpeakerAdapter mAdapter = new SpeakerAdapter(speakers_, this);
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
