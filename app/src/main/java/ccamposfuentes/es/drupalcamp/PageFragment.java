package ccamposfuentes.es.drupalcamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ccamposfuentes.es.apiclient.ApiClient;
import ccamposfuentes.es.apiclient.ApiEndPointInterface;
import ccamposfuentes.es.apiclient.restObject.RestSession;
import ccamposfuentes.es.drupalcamp.adapters.SessionAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.utils.Utils;
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
    private SwipeRefreshLayout mSwipeRefreshLayout;
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

    private void refreshItems() {
        // Connect to database
        if (mDBHelper == null)
            mDBHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);

        mDBHelper.clearSessions();

        getSessions();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...
        loadSessions();

        // Stop refresh animation
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sessions_room);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadSessions();

        // Adaptador
        mAdapter = new SessionAdapter(getContext(), itemsSessions);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public class CustomComparator implements Comparator<Session> {
        @Override
        public int compare(Session o1, Session o2) {
            return o1.getStartDate().compareTo(o2.getStartDate());
        }
    }

    public void loadSessions() {
// Connect to database
        mDBHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);

        Dao dao;
        List sessions;

        try {
            dao = mDBHelper.getSessionDao();

            if (day != null) {

                QueryBuilder<Session, Integer> queryBuilder = dao.queryBuilder();
                queryBuilder.where().eq(Session.ROOM, mPage).and().eq(Session.DAY, day);

                PreparedQuery<Session> preparedQuery = queryBuilder.prepare();

                sessions = dao.query(preparedQuery);
                Collections.sort(sessions, new CustomComparator());

            }
            else {
                sessions = dao.queryForEq(Session.ROOM, mPage);
                Collections.sort(sessions, new CustomComparator());
            }

            itemsSessions = sessions;
        } catch (SQLException e) {
            Log.e("PageFragment", e.toString());
        }

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
        final Call<List<RestSession>> call = client.getSessions("Bearer "
                + Utils.readSharedPrefences(getContext(), getString(R.string.lToken)));

        call.enqueue(new Callback<List<RestSession>>() {
            @Override
            public void onResponse(Call<List<RestSession>> call, Response<List<RestSession>> response) {

                ArrayList<RestSession> items = new ArrayList<>(response.body());

                for (RestSession item : items) {
                    String nid = null;
                    String title = null;
                    String text = null;
                    String difficulty = null;
                    String language = null;
                    String date = null;
                    String room = "2";
                    String type = "session";
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

                    if (item.getRoom().size() > 0)
                        room = item.getRoom().get(0).getValue();

                    if (item.getType() != null)
                        if (item.getType().size() > 0)
                            type = item.getType().get(0).getValue();
                        else
                            type = "session";

                    if (item.getSpeakers().size() > 0)
                        for (int i=0;i<item.getSpeakers().size();i++) {
                            speakers[i] = item.getSpeakers().get(i).getTarget_id();
                        }

                    itemsSessions.add(new Session(
                            nid, title, text, difficulty, language, date,room,speakers, type
                    ));
                }

                for (Session s : itemsSessions) {

                    Dao<Session, Integer> dao;
                    try {
                        dao = mDBHelper.getSessionDao();
                        dao.create(s);
                    } catch (SQLException e) {
                    }
                }

                onItemsLoadComplete();
            }

            @Override
            public void onFailure(Call<List<RestSession>> call, Throwable t) {

                // TODO manage get tourist spots fail
                Log.e("INTRO", "Fail sessions load");
            }

        });
    }
}