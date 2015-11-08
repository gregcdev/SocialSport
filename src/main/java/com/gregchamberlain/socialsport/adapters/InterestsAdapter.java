package com.gregchamberlain.socialsport.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.gregchamberlain.socialsport.R;
import com.gregchamberlain.socialsport.objects.Activity;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by greg on 11/6/15.
 */
public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.InterestsViewHolder> {

    private ArrayList<Activity> activities;
    private ArrayList<Activity> interests;

    private LayoutInflater inflater;
    private Context context;

    private ParseRelation<Activity> relation;

    public InterestsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        activities = new ArrayList<>();
        interests = new ArrayList<>();

        relation = ParseUser.getCurrentUser().getRelation("interests");
    }

    public void setData(ArrayList<Activity> activities, ArrayList<Activity> interests) {
        this.activities = activities;
        this.interests = interests;
        notifyDataSetChanged();
    }

    @Override
    public InterestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.interest_list_item, parent, false);
        return new InterestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final InterestsViewHolder holder, final int position) {
        final Activity activity = activities.get(position);
        holder.interestName.setText(activity.getString("name"));
        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.list_item_icon_size);
        holder.checkBox.setChecked(interests.contains(activity));
        Picasso.with(context)
                .load(activity.getParseFile("icon").getUrl())
                .resize(iconSize, iconSize)
                .centerCrop()
                .into(holder.activityIcon);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {
                    if (!interests.contains(activity)) {
                        interests.add(activity);
                        relation.add(activity);
                    }
                } else {
                    if (interests.contains(activity)) {
                        interests.remove(activity);
                        relation.remove(activity);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

     class InterestsViewHolder extends RecyclerView.ViewHolder{

        private TextView interestName;
        private ImageView activityIcon;
        private CheckBox checkBox;

        public InterestsViewHolder(View itemView) {
            super(itemView);
            interestName = (TextView) itemView.findViewById(R.id.item_name);
            activityIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_checkBox);
        }
    }
}
