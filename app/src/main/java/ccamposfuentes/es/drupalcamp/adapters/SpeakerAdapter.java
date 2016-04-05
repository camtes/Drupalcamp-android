package ccamposfuentes.es.drupalcamp.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.PicassoOnScrollListener;
import ccamposfuentes.es.drupalcamp.R;
import ccamposfuentes.es.drupalcamp.objets.Session;
import ccamposfuentes.es.drupalcamp.objets.Speaker;


/**
 * Author: Carlos Campos
 * Email: carlos@ccamposfuentes.es
 * Date: 06/02/16
 * Project: DrupalCamp
 */

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder> {
    List<Speaker> speakers = new ArrayList<>();
    Context context;
    static final String URL_SERVER = "http://drupalcamp.es/";

    public SpeakerAdapter(List<Speaker> speakers, Context context) {
        this.speakers = speakers;
        this.context = context;
    }

    public static class SpeakerViewHolder extends RecyclerView.ViewHolder {
        public TextView name, company, twitter;
        public CardView card;
        public ImageView image;

        SpeakerViewHolder(View v) {
            super(v);

            card = (CardView) v.findViewById(R.id.cv_speaker_card);
            image = (ImageView) v.findViewById(R.id.iv_speaker_image);
            name = (TextView) v.findViewById(R.id.tv_speaker_name);
            company = (TextView) v.findViewById(R.id.tv_speaker_company);
            twitter = (TextView) v.findViewById(R.id.tv_speaker_twitter);

        }
    }

    @Override
    public SpeakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_speaker_item, null);

        SpeakerViewHolder svh = new SpeakerViewHolder(v);
        return svh;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final SpeakerViewHolder holder, final int position) {

        Speaker speaker = speakers.get(position);

        holder.name.setText(speaker.getUsername());
        holder.company.setText(speaker.getCompany());
        holder.twitter.setText(speaker.getTwitter());
        if (speaker.getImage() != null) {
            String urlimage = URL_SERVER+speaker.getImage();
            Picasso.with(context)
                    .load(urlimage)
                    .into(holder.image);

            holder.image.setPadding(0,0,0,0);
            holder.image.setBackgroundColor(context.getColor(android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }
}