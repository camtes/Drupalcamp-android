package ccamposfuentes.es.drupalcamp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import ccamposfuentes.es.drupalcamp.adapters.SessionAdapter;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objets.Session;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 28/01/16
 * Project: DrupalCamp
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private List<Session> itemsSessions;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        Toast.makeText(getContext(), mPage+"", Toast.LENGTH_SHORT).show();

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_sessions_room);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Connect to database
        DBHelper mDBHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);

        Dao dao;
        try {
            dao = mDBHelper.getSessionDao();
            List sessions = dao.queryForEq(Session.ROOM, mPage);

            itemsSessions = sessions;
        } catch (SQLException e) {
            Log.e("PageFragment", e.toString());
        }

        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }

        // Adaptador
        SessionAdapter mAdapter = new SessionAdapter(getContext(), itemsSessions);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}