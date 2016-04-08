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

import ccamposfuentes.es.drupalcamp.adapters.PageAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DBHelper mDBHelper;
    private static final String json = "{\n" +
            "\"speakers\": [\n" +
            "  {\n" +
            "    \"name\": \"Pako Garcia\",\n" +
            "    \"username\": \"pakmanlh\",\n" +
            "    \"company\": \"Ymbra\",\n" +
            "    \"twitter\": \"@pakmanlh\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/nuestro-amigo-flexbox\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Kasper Myram\",\n" +
            "    \"username\": \"myram\",\n" +
            "    \"company\": \"X-Team\",\n" +
            "    \"twitter\": \"@myram\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-644-1456169490.png\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/using-scrum-agile-remote-development\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Daniel Wehner\",\n" +
            "    \"username\": \"dawehner\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-804-1459165182.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/rest-drupal-8\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Víctor Rodríguez Lledó\",\n" +
            "    \"username\": \"vlledo\",\n" +
            "    \"company\": \"Delirium Coder\",\n" +
            "    \"twitter\": \"@vlledo\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-720-1456995491.png\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/arquitectura-de-la-información-drupal-8\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Rodrigo Aguilera\",\n" +
            "    \"username\": \"rodrigoaguilera\",\n" +
            "    \"company\": \"Ymbra\",\n" +
            "    \"twitter\": \"@Marinero\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-610-1455315082.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/bajarse-al-behat-haz-que-tu-aplicación-se-comporte\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Floris van Geel\",\n" +
            "    \"username\": \"040lab\",\n" +
            "    \"company\": \"040Lab\",\n" +
            "    \"twitter\": \"@040lab\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-760-1457535735.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/geospatial-data-3d-infrastructure-and-iot\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Mateu Aguiló Bosch\",\n" +
            "    \"username\": \"e0ipso\",\n" +
            "    \"company\": \"Lullabot\",\n" +
            "    \"twitter\": \"@e0ipso\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-584-1455007007.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/future-rest-drupal\",\n" +
            "      \"sessions/building-fastest-drupal-earth\",\n" +
            "      \"sessions/unit-testing-drupal\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Claudia Giommarini\",\n" +
            "    \"username\": \"claudia\",\n" +
            "    \"company\": \"idealista\",\n" +
            "    \"twitter\": \"@cocinaitaliana\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/cómo-sacarle-partido-tu-pagina-con-el-marketing-de-contenidos\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Jose Luis Bellido Rojas\",\n" +
            "    \"username\": \"jlbellido\",\n" +
            "    \"company\": \"Cocomore AG\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-732-1457348580.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/sácale-el-jugo-los-display-modes\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Sebastian Siemssen\",\n" +
            "    \"username\": \"fubhy\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-815-1459164752.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/graphql-meets-drupal\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Ignacio Sánchez Holgueras\",\n" +
            "    \"username\": \"isholgueras\",\n" +
            "    \"company\": \"Hackity\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-582-1455002254.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/migrando-datos-drupal-8\",\n" +
            "      \"sessions/hoy-estoy-muy-ansible\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Agustin Llamas Ruiz\",\n" +
            "    \"username\": \"rabbitlair\",\n" +
            "    \"company\": \"SwiftCircle, Desa4 Software\",\n" +
            "    \"twitter\": \"@rabbitlair\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-609-1455281480.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/seguridad-para-todos\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Miguel Ángel Caro García\",\n" +
            "    \"username\": \"miguelkode\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/¡twig-desde-0\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Jorge Díaz Amigo\",\n" +
            "    \"username\": \"jorgillo\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/¡twig-desde-0\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Montaña Franco\",\n" +
            "    \"username\": \"monfranco\",\n" +
            "    \"company\": \"everis\",\n" +
            "    \"twitter\": \"@mon__franco\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-603-1457098267.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/how-contribute-your-daily-work\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Christian López Espínola\",\n" +
            "    \"username\": \"penyaskito\",\n" +
            "    \"company\": \"Lingotek\",\n" +
            "    \"twitter\": \"@penyaskito\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-593-1458659911.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/making-your-entities-and-fields-translatable-drupal-8\",\n" +
            "      \"sessions/unit-testing-drupal\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Javier Gómez\",\n" +
            "    \"username\": \"codigoweb\",\n" +
            "    \"company\": \"Introbay\",\n" +
            "    \"twitter\": \"@fjgomez2\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-704-1456850117.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/dockerdrupal-una-imagen-vale-más-que-mil-palabras\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Ignacio López Flores\",\n" +
            "    \"username\": \"ignaciolflores\",\n" +
            "    \"company\": \"Introbay\",\n" +
            "    \"twitter\": \"@ignaciolflores\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-705-1456850051.png\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/dockerdrupal-una-imagen-vale-más-que-mil-palabras\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Emma Karayiannis\",\n" +
            "    \"username\": \"emma.maria\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-803-1458105955.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/get-yourself-heard-open-source\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Bart Feenstra\",\n" +
            "    \"username\": \"xano\",\n" +
            "    \"company\": \"Druid\",\n" +
            "    \"twitter\": \"@BartFeenstra\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-752-1457413941.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/drupal-8-plugin-system-extensibility-all\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Pedro González Serrano\",\n" +
            "    \"username\": \"NITEMAN\",\n" +
            "    \"company\": \"sbit.io\",\n" +
            "    \"twitter\": \"@NITEMAN_es\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-797-1457646170.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/talk-cheap\",\n" +
            "      \"sessions/building-fastest-drupal-earth\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"David Gil\",\n" +
            "    \"username\": \"david.gil\",\n" +
            "    \"company\": \"Biko2\",\n" +
            "    \"twitter\": \"@david_gil_biko2\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-592-1455028145.png\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/mejorando-la-experiencia-de-los-editores-de-contenidos-con-paragraphs\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Lauri Eskola\",\n" +
            "    \"username\": \"lauriii\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"@laurii1\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/markup-drupal-8-way\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Joel Rodríguez Alemán\",\n" +
            "    \"username\": \"joelrguezaleman\",\n" +
            "    \"company\": \"Runroom\",\n" +
            "    \"twitter\": \"@joelrguezaleman\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-641-1458247738.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/eventdispatcher-natural-successor-hooks\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Juanen Bernal Carrillo\",\n" +
            "    \"username\": \"jansete\",\n" +
            "    \"company\": \"idealista\",\n" +
            "    \"twitter\": \"@jansev3n\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-657-1456607587.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/activando-tu-amp-versión-qué-necesitas-saber\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Martín González Robles\",\n" +
            "    \"username\": \"mgzrobles\",\n" +
            "    \"company\": \"idealista\",\n" +
            "    \"twitter\": \"@mgzrobles\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-581-1454962148.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/activando-tu-amp-versión-qué-necesitas-saber\",\n" +
            "      \"sessions/varnish-para-meros-mortales\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Janez Urevc\",\n" +
            "    \"username\": \"slashrsm\",\n" +
            "    \"company\": \"Examiner.com\",\n" +
            "    \"twitter\": \"@slashrsm\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-685-1456829635.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/next-generation-embedding-entity-embed\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Josef Dabernig\",\n" +
            "    \"username\": \"dasjo\",\n" +
            "    \"company\": \"Amazee Labs\",\n" +
            "    \"twitter\": \"@dasjo\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-599-1455061077.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/ruling-drupal-8-d8rules\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"manuel rodriguez\",\n" +
            "    \"username\": \"manu\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/design-sprint-toolkit-product-innovation\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Carlos Campos Fuentes\",\n" +
            "    \"username\": \"ccamposfuentes\",\n" +
            "    \"company\": \"SI2 Soluciones\",\n" +
            "    \"twitter\": \"@ccamposf\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-617-1455576429.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/una-aplicación-real-de-drupal-8-como-servidor-restful-backoffice-para-apps\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Marcelo Tosco\",\n" +
            "    \"username\": \"capynet\",\n" +
            "    \"company\": \"Front.id\",\n" +
            "    \"twitter\": \"@capynet\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-576-1453407680.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/una-aplicación-real-de-drupal-8-como-servidor-restful-backoffice-para-apps\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Stelios Kourakis\",\n" +
            "    \"username\": \"skourak\",\n" +
            "    \"company\": \"pointblank.gr\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-723-1457011445.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/designing-success-content-first-design\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Ramon Vilar\",\n" +
            "    \"username\": \"rvilar\",\n" +
            "    \"company\": \"Ymbra\",\n" +
            "    \"twitter\": \"@rvilar\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/ganar-y-entregar-grandes-proyectos-desde-la-perspectiva-de-una-pequeña-empresa\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Cristina Chumillas\",\n" +
            "    \"username\": \"ckrina\",\n" +
            "    \"company\": \"Ymbra\",\n" +
            "    \"twitter\": \"@chumillas\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-668-1457872466.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/mejorando-el-proceso-responsive-web-design-en-2016\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Juampy NR\",\n" +
            "    \"username\": \"juampynr\",\n" +
            "    \"company\": \"Lullabot\",\n" +
            "    \"twitter\": \"@juampynr\",\n" +
            "    \"image\": \"sites/default/files/styles/medium/public/pictures/picture-590-1455016669.jpg\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/debugging-drupal-8\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"name\": \"Kalin CHERNEV\",\n" +
            "    \"username\": \"kalinchernev\",\n" +
            "    \"company\": \"\",\n" +
            "    \"twitter\": \"\",\n" +
            "    \"image\": \"\",\n" +
            "    \"session\": [\n" +
            "      \"sessions/style-guides-drupal-development-workflows\"\n" +
            "    ]\n" +
            "  }\n" +
            "],\n" +
            "\"sessions\": [\n" +
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
            "      \"date\":\"24/04/2016 11:30:00\",\n" +
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
            "      \"date\":\"24/04/2016 14:00:00\",\n" +
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
            "      \"date\":\"24/04/2016 11:30:00\",\n" +
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
            "      \"date\":\"24/04/2016 14:00:00\",\n" +
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
            "      \"date\":\"24/04/2016 11:30:00\",\n" +
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
            "      \"date\":\"24/04/2016 14:00:00\",\n" +
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
            "      \"date\":\"24/04/2016 11:30:00\",\n" +
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
            "      \"date\":\"24/04/2016 14:00:00\",\n" +
            "      \"room\":4,\n" +
            "      \"speaker\":\"\",\n" +
            "      \"type\":1\n" +
            "    },\n" +
            "  ]\n" +
            "}\n";


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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nestedScroll);
        scrollView.setFillViewport (true);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        String mDay = null;
        if (getIntent().hasExtra("day"))
            mDay = getIntent().getExtras().getString("day");

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),
                MainActivity.this, mDay));

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

    public void saveDatabase() {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonSpeakers = jsonObject.getJSONArray("speakers");
            JSONArray jsonSessions = jsonObject.getJSONArray("sessions");

            for (int i=0; i<jsonSpeakers.length(); i++) {
                Dao<Speaker, Integer> dao;
                JSONObject obj = jsonSpeakers.getJSONObject(i);
                Speaker speaker;

                try {
                    dao = mDBHelper.getSpeakerDao();
                    String[] session = new String[10];
                    JSONArray sessions = obj.getJSONArray(Speaker.SESSIONS);

                    if (sessions.length() >= 0) {
                        for (int j = 0; j < sessions.length(); j++) {
                            session[j] = sessions.getString(j);
                        }
                    }

                    if ((obj.getString(Speaker.IMAGE).equals(""))) {
                        speaker = new Speaker(obj.getString(Speaker.USERNAME), obj.getString(Speaker.COMPANY),
                                obj.getString(Speaker.TWITTER), session);
                    }
                    else {
                        speaker = new Speaker(obj.getString(Speaker.USERNAME), obj.getString(Speaker.COMPANY),
                                obj.getString(Speaker.TWITTER), obj.getString(Speaker.IMAGE), session);
                    }

                    dao.create(speaker);
                } catch (SQLException e) {
                    Log.e("MainActivity", e.toString());
                }
            }

            for (int i=0; i<jsonSessions.length(); i++) {
                Dao<Session, Integer> dao;
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
