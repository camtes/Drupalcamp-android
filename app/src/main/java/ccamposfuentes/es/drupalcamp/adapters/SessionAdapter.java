package ccamposfuentes.es.drupalcamp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.R;
import ccamposfuentes.es.drupalcamp.SessionDetail;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objects.Session;
import ccamposfuentes.es.drupalcamp.objects.Speaker;


/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 17/02/16
 * Project: DrupalCamp
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    List<Session> itemsSessions;
    DBHelper mDBHelper;
    Context context;

    public SessionAdapter(Context context, List<Session> itemsSessions) {
        this.context = context;
        this.itemsSessions = itemsSessions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, speaker, hour;
        public CardView card;

        ViewHolder(View v) {
            super(v);

            card = (CardView) v.findViewById(R.id.cv_schedule);
            title = (TextView) v.findViewById(R.id.tv_schedule_title);
            speaker = (TextView) v.findViewById(R.id.tv_schedule_speaker);
            hour = (TextView) v.findViewById(R.id.tv_schedule_hour);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return itemsSessions.get(position).getIntType();
    }

    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;

        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_room_item, null);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_room_item_special, null);
                break;
        }

        ViewHolder svh = new ViewHolder(v);

        return svh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        switch (itemsSessions.get(position).getIntType()) {
            case 0:
                List<String> speakersSessions = new ArrayList<>();
                DBHelper mDBHelper = OpenHelperManager.getHelper(context, DBHelper.class);

                for (String s : itemsSessions.get(position).getSpeakers()) {
                    Dao<Speaker, Integer> dao;
                    try {
                        dao = mDBHelper.getSpeakerDao();
                        Speaker aux = dao.queryForId(Integer.valueOf(s));
                        speakersSessions.add(aux.getUsername());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                String speakers = "";
                for (int i=0; i<speakersSessions.size(); i++) {
                    if (i == 0)
                        speakers = speakersSessions.get(i);
                    else
                        speakers = speakers+", "+speakersSessions.get(i);
                }

                holder.title.setText(itemsSessions.get(position).getTitle());
                holder.speaker.setText(speakers);
                holder.hour.setText(itemsSessions.get(position).getHour());
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SessionDetail.class);
                        intent.putExtra("idSession", itemsSessions.get(position).getId());
                        Log.i("SessionAdapter", "Load sessionId = "+itemsSessions.get(position).getId());
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.title.setText(itemsSessions.get(position).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        return itemsSessions.size();
    }
}
