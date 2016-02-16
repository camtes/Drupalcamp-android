package ccamposfuentes.es.drupalcamp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import ccamposfuentes.es.drupalcamp.adapters.PageAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objets.Session;
import ccamposfuentes.es.drupalcamp.objets.Speaker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper mDBHelper;
    private static final String json = "{'speakers':[{'name':'speaker1','company':'company1','twitter':'@speaker1'}, {'name':'speaker2','company':'company2','twitter':'@speaker2'}], 'sessions':[{'name':'session1','date':'23/04/2016 10:00:00', 'room':1,'speaker':'@speaker1'}, {'name':'session2','date':'24/04/2016 12:00:00', 'room':3,'speaker':'@speaker2'} ]}";

    private RelativeLayout rl_friday, rl_saturday, rl_sunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nestedScroll);
        scrollView.setFillViewport (true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),
                MainActivity.this));

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
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

            saveDatabase();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_today) {

        } else if (id == R.id.nav_speakers) {
            startActivity(new Intent(this, SpeakerActivity.class));
        } else if (id == R.id.nav_fav) {

        } else if (id == R.id.nav_share) {

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

    public void saveDatabase() {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonSpeakers = jsonObject.getJSONArray("speakers");
            JSONArray jsonSessions = jsonObject.getJSONArray("sessions");

            for (int i=0; i<jsonSpeakers.length(); i++) {
                Dao dao;
                JSONObject obj = jsonSpeakers.getJSONObject(i);

                try {
                    dao = mDBHelper.getSpeakerDao();
                    Speaker speaker = new Speaker(obj.getString(Speaker.NAME), obj.getString(Speaker.COMPANY),
                            obj.getString(Speaker.TWITTER));
                    dao.create(speaker);
                } catch (SQLException e) {
                    Log.e("MainActivity", "Error creando el ponente");
                }
            }

            for (int i=0; i<jsonSpeakers.length(); i++) {
                Dao dao;
                Dao daoSpeaker;
                JSONObject obj = jsonSessions.getJSONObject(i);

                try {
                    daoSpeaker = mDBHelper.getSpeakerDao();
                    List<Speaker> speaker = daoSpeaker.queryForEq(Speaker.TWITTER, obj.get(Session.SPEAKER));
                    dao = mDBHelper.getSessionDao();
                    Session session = new Session(obj.getString(Session.NAME), obj.getString(Session.DATE),
                            obj.getInt(Session.ROOM), speaker.get(0));
                    dao.create(session);
                } catch (SQLException e) {
                    Log.e("MainActivity", "Error creando la sesión");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
