package ccamposfuentes.es.drupalcamp;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.utils.Utils;

public class SessionDetail extends AppCompatActivity {

    private Session session;
    private ImageButton ibStart1, ibStart2, ibStart3, ibStart4, ibStart5;
    private TextView title, sumary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);

        if (getSupportActionBar() != null)
            getSupportActionBar().setElevation(0);

        title = (TextView) findViewById(R.id.tv_session_detail_title);
        sumary = (TextView) findViewById(R.id.tv_session_detail_sumary);
        ibStart1 = (ImageButton) findViewById(R.id.ib_start_1);
        ibStart2 = (ImageButton) findViewById(R.id.ib_start_2);
        ibStart3 = (ImageButton) findViewById(R.id.ib_start_3);
        ibStart4 = (ImageButton) findViewById(R.id.ib_start_4);
        ibStart5 = (ImageButton) findViewById(R.id.ib_start_5);

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
            loadSession(bundle.getInt("idSession"));
        }

    }

    public void loadSession(int id) {
        // Connect to database
        DBHelper mDBHelper = OpenHelperManager.getHelper(getApplicationContext(), DBHelper.class);

        Dao dao;

        try {
            dao = mDBHelper.getSessionDao();
            session = (Session) dao.queryForId(id);
            if (session != null)
                setSession();
            Log.i("SessionDetail", "Load session.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set session UI data
     */
    public void setSession() {
        title.setText(session.getName());
    }
}
