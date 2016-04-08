package ccamposfuentes.es.drupalcamp.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;


/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 09/02/16
 * Project: DrupalCamp
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "drupalcamp2016.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Speaker, Integer> usuarioDao;
    private Dao<Session, Integer> grupoDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Speaker.class);
            TableUtils.createTable(connectionSource, Session.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);
    }

    public Dao<Speaker, Integer> getSpeakerDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Speaker.class);
        }
        return usuarioDao;
    }

    public Dao<Session, Integer> getSessionDao() throws SQLException {
        if (grupoDao == null) {
            grupoDao = getDao(Session.class);
        }
        return grupoDao;
    }

    @Override
    public void close() {
        super.close();
        usuarioDao = null;
        grupoDao = null;
    }

}