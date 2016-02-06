package ccamposfuentes.es.drupalcamp.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ccamposfuentes.es.drupalcamp.R;
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

    @Override
    public void onBindViewHolder(final SpeakerViewHolder holder, final int position) {

        holder.name.setText(speakers.get(position).getName());
        holder.company.setText(speakers.get(position).getCompany());
        holder.twitter.setText(speakers.get(position).getTwitter());
        if (speakers.get(position).getImage() != null) {
            Picasso.with(context).load(speakers.get(position).getImage()).into(holder.image);
            holder.image.setPadding(0,0,0,0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.image.setImageTintList(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }
}