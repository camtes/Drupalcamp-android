package ccamposfuentes.es.drupalcamp.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.R;
import ccamposfuentes.es.drupalcamp.objects.Speaker;

/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 12/04/16
 * Project: DrupalCamp
 */

public class SpeakersSessionAdapter extends
        RecyclerView.Adapter<SpeakersSessionAdapter.SpeakerViewHolder> {

    List<Speaker> speakers = new ArrayList<>();
    Context context;

    public SpeakersSessionAdapter(List<Speaker> speakers, Context context) {
        this.speakers = speakers;
        this.context = context;
    }

    public static class SpeakerViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        public TextView name;

        SpeakerViewHolder(View v) {
            super(v);

//            card = (CardView) v.findViewById(R.id.cv_speaker_card);
            name = (TextView) v.findViewById(R.id.tv_speaker_name);
        }
    }

    @Override
    public SpeakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_speaker_session_item, null);

        SpeakerViewHolder svh = new SpeakerViewHolder(v);

        return svh;
    }

    @Override
    public void onBindViewHolder(final SpeakerViewHolder holder, final int position) {

        Speaker speaker = speakers.get(position);

        holder.name.setText(speaker.getName());
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }

}
