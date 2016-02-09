package ccamposfuentes.es.drupalcamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.adapters.SpeakerAdapter;
import ccamposfuentes.es.drupalcamp.objets.Speaker;

public class SpeakerActivity extends AppCompatActivity {

    List<Speaker> speakers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        speakers = new ArrayList<>();
        speakers.add(new Speaker("Carlos Campos", "SI2 Soluciones", "@ccamposf",
                "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAATSAAAAJGE1ZDUxYjlkLWFkMTEtNGYzZS1iNDQ4LTIzMWExNTIxZjUxNA.jpg"));

        for (int i=0; i<3; i++) {
            speakers.add(new Speaker("Ponente "+i, "Empresa "+i, "twitter"));
        }

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_speakers);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        SpeakerAdapter mAdapter = new SpeakerAdapter(speakers, this);
        mRecyclerView.setAdapter(mAdapter);

    }
}
