package ccamposfuentes.es.drupalcamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.apiclient.ApiClient;
import ccamposfuentes.es.apiclient.ApiEndPointInterface;
import ccamposfuentes.es.apiclient.restObject.RestSession;
import ccamposfuentes.es.drupalcamp.adapters.SessionAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 28/01/16
 * Project: DrupalCamp
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_DAY = "ARG_DAY";

    private int mPage;
    private List<Session> itemsSessions;
    private String day;
    private RecyclerView mRecyclerView;
    private SessionAdapter mAdapter;
    private DBHelper mDBHelper;

    public static PageFragment newInstance(int page, String day) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_DAY, day);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        day = getArguments().getString(ARG_DAY);
        itemsSessions = new ArrayList<>();

        // Connect to database
        mDBHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sessions_room);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Connect to database
        DBHelper mDBHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);

        Dao dao;
        List sessions;

        try {
            dao = mDBHelper.getSessionDao();

            if (day != null) {

                QueryBuilder<Session, Integer> queryBuilder = dao.queryBuilder();
                queryBuilder.where().eq(Session.ROOM, mPage).and().eq(Session.DAY, day);

                PreparedQuery<Session> preparedQuery = queryBuilder.prepare();

                sessions = dao.query(preparedQuery);

            }
            else {
                sessions = dao.queryForEq(Session.ROOM, mPage);
            }

            itemsSessions = sessions;
        } catch (SQLException e) {
            Log.e("PageFragment", e.toString());
        }

        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }

        // Adaptador
        mAdapter = new SessionAdapter(getContext(), itemsSessions);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }


}