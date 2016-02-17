package ccamposfuentes.es.drupalcamp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import ccamposfuentes.es.drupalcamp.R;
import ccamposfuentes.es.drupalcamp.database.DBHelper;
import ccamposfuentes.es.drupalcamp.objets.Session;
import ccamposfuentes.es.drupalcamp.objets.Speaker;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 17/02/16
 * Project: DrupalCamp
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    List<Session> itemsSessions;
    Context context;

    public SessionAdapter(Context context, List<Session> itemsSessions) {
        this.context = context;
        this.itemsSessions = itemsSessions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, speaker, speakerTwitter, hour;
        public CardView card;

        ViewHolder(View v) {
            super(v);

            card = (CardView) v.findViewById(R.id.cv_schedule);
            title = (TextView) v.findViewById(R.id.tv_schedule_title);
            speaker = (TextView) v.findViewById(R.id.tv_schedule_speaker);
            speakerTwitter = (TextView) v.findViewById(R.id.tv_schedule_speaker_twitter);
            hour = (TextView) v.findViewById(R.id.tv_schedule_hour);
        }
    }

    @Override
    public SessionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_room_item, null);

        ViewHolder svh = new ViewHolder(v);

        return svh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Speaker speaker = null;

        // Connect to database
        DBHelper mDBHelper = OpenHelperManager.getHelper(context, DBHelper.class);

        Dao dao;
        try {
            dao = mDBHelper.getSpeakerDao();
            speaker = (Speaker) dao.queryForId(itemsSessions.get(position).getSpeaker().getId());
            Log.e("speaker", speaker.getName());

        } catch (SQLException e) {
            Log.e("SpeakerActivity", e.toString());
        }

        holder.title.setText(itemsSessions.get(position).getName());
        holder.speaker.setText(speaker.getName());
        holder.speakerTwitter.setText(speaker.getTwitter());
        holder.hour.setText(itemsSessions.get(position).getHour());
    }

    @Override
    public int getItemCount() {
        return itemsSessions.size();
    }
}
