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
    private static final String json = "{\n" +
            "  \"speakers\": [\n" +
            "    {\n" +
            "      \"name\":\"speaker1\",\n" +
            "      \"company\":\"company1\",\n" +
            "      \"twitter\":\"@speaker1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"speaker2\",\n" +
            "      \"company\":\"company2\",\n" +
            "      \"twitter\":\"@speaker2\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"sessions\": [\n" +
            "    {\n" +
            "      \"name\":\"Bienvenidos a esta nuestra comunidad\",\n" +
            "      \"date\":\"23/04/2016 9:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"pdelgado, nesimo, jparra, ignacioflores, jansete\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Arquitectura de la información & Drupal 8\",\n" +
            "      \"date\":\"23/04/2016 10:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"vlledo\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Cómo sacarle partido a tu página con el Marketing de contenidos\",\n" +
            "      \"date\":\"23/04/2016 12:00:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"Claudia\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Seguridad para todos\",\n" +
            "      \"date\":\"23/04/2016 13:00:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"rabbitlair\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Almuerzo\",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Proximamente\",\n" +
            "      \"date\":\"23/04/2016 15:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Talk is cheap\",\n" +
            "      \"date\":\"23/04/2016 16:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"NITEMAN\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Asamblea\",\n" +
            "      \"date\":\"23/04/2016 17:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Proximamente\",\n" +
            "      \"date\":\"24/04/2016 9:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Design Sprint: a toolkit for product innovation\",\n" +
            "      \"date\":\"24/04/2016 10:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"manu\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Conseguir y entregar grandes proyctos desde la perspectiva de una pequeña empresa\",\n" +
            "      \"date\":\"24/04/2016 12:00:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"rvillar\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Proximamente\",\n" +
            "      \"date\":\"24/04/2016 10:00:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Sesión de cierre  \",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":1,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Nuestro amigo Flexbox\",\n" +
            "      \"date\":\"23/04/2016 9:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"pakmanlh\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Bajarse al behat: haz que tu aplicación se comporte\",\n" +
            "      \"date\":\"23/04/2016 10:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"rodrigoaguilera\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Sácale el jugo a los display modes\",\n" +
            "      \"date\":\"23/04/2016 12:00:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"jbellido\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"¡TWIG desde 0!\",\n" +
            "      \"date\":\"23/04/2016 13:00:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"miguel_kode, Jorgillo\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Almuerzo\",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Docker & Drupal: Una imágen vale más que mil palabras\",\n" +
            "      \"date\":\"23/04/2016 15:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"codigoweb, ignacioflores\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Mejorando la experiencia de los editores de contenidos con Paragraphs\",\n" +
            "      \"date\":\"23/04/2016 16:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"david.gil\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Asamblea\",\n" +
            "      \"date\":\"23/04/2016 17:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Activando tu AMP versión. Qué necesitas saber\",\n" +
            "      \"date\":\"24/04/2016 9:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"jansete, mgzrobles\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Una aplicación real de Drupal 8 como servidor RESTful / Backoffice para apps\",\n" +
            "      \"date\":\"24/04/2016 10:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"ccamposfuentes, capynet\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Varnish para meros mortales\",\n" +
            "      \"date\":\"24/04/2016 12:00:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"mgzrobles\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Hoy estoy muy Ansible\",\n" +
            "      \"date\":\"24/04/2016 10:00:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"isholgueras\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Sesión de cierre  \",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":2,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Using Scrum Agile in remote development\",\n" +
            "      \"date\":\"23/04/2016 9:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"myram\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Geospatail Data, 3d Infrastructure and IoT\",\n" +
            "      \"date\":\"23/04/2016 10:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"040lab\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"GraphQL meets Drupal\",\n" +
            "      \"date\":\"23/04/2016 12:00:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"fubhy\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"How to contribute in your day to day work\",\n" +
            "      \"date\":\"23/04/2016 13:00:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"mon_franco\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Almuerzo\",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Get yourself heard in Open Source\",\n" +
            "      \"date\":\"23/04/2016 15:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"emma.maria\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Markup the Drupal 8 way\",\n" +
            "      \"date\":\"23/04/2016 16:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"lauriii\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Asamblea\",\n" +
            "      \"date\":\"23/04/2016 17:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"The next generation embedding with Entity embed\",\n" +
            "      \"date\":\"24/04/2016 9:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"slashrsm\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Designing for Success: Content-first Design\",\n" +
            "      \"date\":\"24/04/2016 10:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"skourak\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Improving the Responsive Web Design Process in 2016\",\n" +
            "      \"date\":\"24/04/2016 12:00:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"ckrina\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Style guides in Drupal development workflows\",\n" +
            "      \"date\":\"24/04/2016 10:00:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"kalinchernev\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Sesión de cierre  \",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":3,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"REST in Drupal 8\",\n" +
            "      \"date\":\"23/04/2016 9:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"dawehner\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"The future of REST in Drupal\",\n" +
            "      \"date\":\"23/04/2016 10:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"e0ipso\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Migrating data to Drupal 8\",\n" +
            "      \"date\":\"23/04/2016 12:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"isholgueras\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Making your entities and fields translatable with Drupal 8\",\n" +
            "      \"date\":\"23/04/2016 13:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"penyaskito\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Almuerzo\",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"The Drupal 8 plugin system: extensibility for all\",\n" +
            "      \"date\":\"23/04/2016 15:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"Xano\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"EventDispatcher: the natural successor of hooks\",\n" +
            "      \"date\":\"23/04/2016 16:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"joelrguezaleman\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Asamblea\",\n" +
            "      \"date\":\"23/04/2016 17:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Ruling Drupal 8 with #d8rules\",\n" +
            "      \"date\":\"24/04/2016 9:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"dasjo\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Building the fastest Drupal in the Galaxy\",\n" +
            "      \"date\":\"24/04/2016 10:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"e0ipso, NITEMAN\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Desayuno\",\n" +
            "      \"date\":\"23/04/2016 11:30:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Debugging in Drupal 8\",\n" +
            "      \"date\":\"24/04/2016 12:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"juampynr\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Unit testint in Drupal\",\n" +
            "      \"date\":\"24/04/2016 10:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"e0ipso, penyaskito\",\n" +
            "      \"type\":0\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\":\"Sesión de cierre  \",\n" +
            "      \"date\":\"23/04/2016 14:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "  ]\n" +
            "}\n";

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
                    Log.e("MainActivity", e.toString());
                }
            }

            for (int i=0; i<jsonSessions.length(); i++) {
                Dao dao;
                JSONObject obj = jsonSessions.getJSONObject(i);

                try {
                    dao = mDBHelper.getSessionDao();
                    Session session = new Session(obj.getString(Session.NAME), obj.getString(Session.DATE),
                            obj.getInt(Session.ROOM), obj.getString(Session.SPEAKER), obj.getInt(Session.TYPE));
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
