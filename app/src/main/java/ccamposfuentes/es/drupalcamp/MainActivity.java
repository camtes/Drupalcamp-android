package ccamposfuentes.es.drupalcamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.apiclient.ApiClient;
import ccamposfuentes.es.apiclient.ApiEndPointInterface;
import ccamposfuentes.es.apiclient.restObject.RestTarget;
import ccamposfuentes.es.drupalcamp.adapters.PageAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;
import ccamposfuentes.es.apiclient.restObject.RestSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Session> sessionsList;
    DBHelper mDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneSignal.startInit(this).init();
        String id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nestedScroll);
        assert scrollView != null;
        scrollView.setFillViewport (true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        String mDay = null;
        if (getIntent().hasExtra("day"))
            mDay = getIntent().getExtras().getString("day");

        assert viewPager != null;
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),
                MainActivity.this, mDay));

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        assert tabLayout != null;
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        // Connect to database
        mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);

        // Save json if db is empty
        SharedPreferences sharedPreferences = getSharedPreferences("DrupalCamp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("database", false)) {
            editor.putBoolean("database", true);
            editor.apply();

            //saveDatabase();
        }

        getSessions();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_speakers) {
            startActivity(new Intent(this, SpeakerActivity.class));
        } else if (id == R.id.nav_fav) {

        } else if (id == R.id.nav_calendar_saturday) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("day", "Saturday");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else if (id == R.id.nav_calendar_sunday) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("day", "Sunday");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Descargarte ya la app de la Drupalcamp 2016!";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
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
                sessionsList = new ArrayList<>();

                for (RestSession item : items) {
                    String nid = null;
                    String title = null;
                    String text = null;
                    String difficulty = null;
                    String language = null;
                    String date = null;
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

                    if (item.getSpeakers().size() > 0)
                        for (int i=0;i<item.getSpeakers().size();i++) {
                            speakers[i] = item.getSpeakers().get(i).getTarget_id();
                        }

                    sessionsList.add(new Session(
                            nid, title, text, difficulty, language, date,1,speakers,0
                    ));
                }

                for (Session s : sessionsList) {

                    Dao<Session, Integer> dao;
                    try {
                        dao = mDBHelper.getSessionDao();
                        dao.create(s);
                    } catch (SQLException e) {
                        Log.e("MainActivity", "Error creando la sesión");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RestSession>> call, Throwable t) {

                // TODO manage get tourist spots fail
//                dataDownloaded = false;
                Log.e("INTRO", "Fail sessions load");
            }
        });
    }
}
